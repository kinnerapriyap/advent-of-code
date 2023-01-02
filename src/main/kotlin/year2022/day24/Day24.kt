package year2022.day24

import utils.data.Point
import java.io.File

enum class Type(val row: Int, val col: Int) {
    UP(-1, 0), DOWN(1, 0), RIGHT(0, +1), LEFT(0, -1), STAY(0, 0)
}

data class Position(val point: Point, val type: Type) {
    fun move(maxRow: Int, maxCol: Int): Position {
        val newRow = (point.row + type.row).let { if (it in 1..maxRow) it else if (it < 1) maxRow else 1 }
        val newCol = (point.col + type.col).let { if (it in 1..maxCol) it else if (it < 1) maxCol else 1 }
        return Position(Point(newRow, newCol), type)
    }
}

fun part1(lines: List<CharArray>): Int = lines.resolve()

fun part2(lines: List<CharArray>): Int = lines.resolve(trips = 3)

fun main() {
    val lines = File("src/main/kotlin/year2022.day24/day24_input.txt")
        .bufferedReader()
        .readLines().map { it.toCharArray() }
    println(part1(lines))
    println(part2(lines))
}

fun List<CharArray>.resolve(trips: Int = 1): Int {
    val start = Point(0, 1)
    val end = Point(lastIndex, get(0).lastIndex - 1)
    var current = hashSetOf(start)
    val endValues = mutableListOf(end, start, end)
    var snow = mapIndexed { row, chars -> chars.mapIndexed { col, c -> c to Point(row, col) } }.flatten()
        .filter { it.first in listOf('>', '<', '^', 'v') }
        .map { (c, point) ->
            val type = when (c) {
                '>' -> Type.RIGHT
                '<' -> Type.LEFT
                '^' -> Type.UP
                'v' -> Type.DOWN
                else -> error("beep")
            }
            Position(point, type)
        }
    var i = 1
    while (true) {
        snow = snow.map { it.move(lastIndex - 1, get(0).lastIndex - 1) }
        current = current.flatMap { (row, col) ->
            Type.values().map { Point(row + it.row, col + it.col) }
                .filter { p ->
                    p == start || p == end ||
                            (p.row in 1 until lastIndex && p.col in 1 until get(0).lastIndex)
                }
                .filter { p -> !snow.map { it.point }.contains(p) }
        }.toHashSet()
        if (current.contains(endValues.first())) {
            current = hashSetOf(endValues.removeFirst())
            if (trips == 1 || endValues.isEmpty()) return i
        }
        i++
    }
}