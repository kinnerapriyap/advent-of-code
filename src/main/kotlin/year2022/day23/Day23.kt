package year2022.day23

import year2022.day23.Direction.Companion.canMove
import year2022.day23.Direction.Companion.movableOrder
import year2022.day23.Direction.Companion.newPoint
import utils.data.Point
import java.io.File
import java.util.Collections

const val rounds = 10

fun part1(lines: List<List<Char>>): Int {
    val grid = lines.map { it.toMutableList() }.toMutableList().apply { (1..11).forEach { _ -> addBoundary() } }
    val proposals = grid.mapIndexed { row, chars ->
        chars.mapIndexedNotNull { col, c -> if (c == '#') Proposal(Point(row, col), null) else null }
    }.flatten().toMutableList()
    (0 until rounds).forEach { round ->
        proposals.makeProposals(grid, round)
        grid.implement(proposals)
    }
    return grid.smallestRectangleContainingElves().fold(0) { acc, e -> acc + e.count { it == '.' } }
}

fun part2(lines: List<List<Char>>): Int {
    val grid = lines.map { it.toMutableList() }.toMutableList().apply { (1..100).forEach { _ -> addBoundary() } }
    val proposals = grid.mapIndexed { row, chars ->
        chars.mapIndexedNotNull { col, c -> if (c == '#') Proposal(Point(row, col), null) else null }
    }.flatten().toMutableList()
    var oldProposals: List<Proposal>? = null
    var i = 0
    while (proposals != oldProposals) {
        oldProposals = proposals.toList()
        proposals.makeProposals(grid, i)
        grid.implement(proposals)
        i++
    }
    return i
}

fun main() {
    val lines = File("src/main/kotlin/year2022.day23/day23_input.txt")
        .bufferedReader()
        .readLines()
        .map { line -> line.toList() }
    println(part1(lines))
    println(part2(lines))
}

data class Proposal(val old: Point, val new: Point?)

fun MutableList<MutableList<Char>>.implement(proposals: MutableList<Proposal>) {
    val g = proposals.groupBy { it.new }
    for (i in proposals.indices) {
        val (old, new) = proposals[i]
        if (new != null && g[new]!!.count() == 1) {
            this[old.row][old.col] = '.'
            this[new.row][new.col] = '#'
            proposals[i].new?.let { proposals[i] = proposals[i].copy(old = it, new = null) }
        }
    }
}

fun MutableList<Proposal>.makeProposals(grid: MutableList<MutableList<Char>>, round: Int) {
    val order = movableOrder.toList()
    Collections.rotate(order, -round)
    for (i in indices) {
        val (old, _) = get(i)
        val directions = order.filter { it.canMove(old, grid) }
        this[i] =
            if (directions.size == order.size || directions.isEmpty()) Proposal(old, null)
            else Proposal(old, directions.first().newPoint(old))
    }
}

enum class Direction(val offsetR: Int, val offsetC: Int) {
    N(-1, 0), S(1, 0), E(0, 1), W(0, -1),
    NE(-1, 1), NW(-1, -1), SE(1, 1), SW(1, -1);

    companion object {
        val movableOrder = listOf(N, S, W, E)

        fun Direction.canMove(elf: Point, grid: MutableList<MutableList<Char>>): Boolean =
            Direction.values().filter { it.name.contains(this.name) }
                .all {
                    elf.row + it.offsetR in grid.indices && elf.col + it.offsetC in grid[0].indices &&
                            grid[elf.row + it.offsetR][elf.col + it.offsetC] == '.'
                }

        fun Direction.newPoint(elf: Point) = Point(elf.row + offsetR, elf.col + offsetC)
    }
}

fun MutableList<MutableList<Char>>.addBoundary() {
    // Top and bottom rows
    add(0, MutableList(get(0).size) { '.' })
    add(MutableList(get(0).size) { '.' })
    // Start and end cols
    forEach {
        it.add(0, '.')
        it.add('.')
    }
}

fun MutableList<MutableList<Char>>.smallestRectangleContainingElves(): List<List<Char>> {
    val top = indexOfFirst { it.any { e -> e == '#' } }
    val bottom = indexOfLast { it.any { e -> e == '#' } }
    val start = map { it.indexOfFirst { e -> e == '#' } }.filter { it != -1 }.min()
    val end = map { it.indexOfLast { e -> e == '#' } }.filter { it != -1 }.max()
    return subList(top, bottom + 1).map { it.subList(start, end + 1) }
}