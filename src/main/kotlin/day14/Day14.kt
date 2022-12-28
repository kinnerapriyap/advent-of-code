package day14

import utils.data.Point
import java.io.File
import kotlin.math.min
import kotlin.math.max

fun part1(rocks: HashSet<Point>): Int = rocks.fillInSand().size

fun part2(rocks: HashSet<Point>): Int = rocks.addFloor().fillInSand().size

fun main() {
    val rocks = File("src/main/kotlin/day14/day14_input.txt")
        .bufferedReader()
        .readLines()
        .map { line ->
            line.split(" -> ")
                .map { it.split(",") }
                .map { list -> list[0].toInt() to list[1].toInt() }
        }
        .fillInRocks()
    println(part1(rocks))
    println(part2(rocks))
}

val startSand = Point(0, 500)

fun HashSet<Point>.fillInSand(): HashSet<Point> {
    val sand = hashSetOf<Point>()
    val maxRow = maxOf { it.row }
    var current = startSand
    fun isEmpty(sandPt: Point): Boolean =
        !contains(Point(sandPt.row, sandPt.col)) && !sand.contains(sandPt) && sandPt.row <= maxRow
    while (true) {
        var isDeadEnd = false
        while (!isDeadEnd) {
            when {
                isEmpty(Point(current.row + 1, current.col)) ->
                    current = current.copy(row = current.row + 1)

                isEmpty(Point(current.row + 1, current.col - 1)) ->
                    current = Point(current.row + 1, current.col - 1)

                isEmpty(Point(current.row + 1, current.col + 1)) ->
                    current = Point(current.row + 1, current.col + 1)

                else -> isDeadEnd = true
            }
            if (isDeadEnd && current.row < maxRow) sand.add(current) else if (isDeadEnd) break
        }
        if (current == startSand || current.row == maxRow) break else current = startSand
    }
    return sand
}

fun List<List<Pair<Int, Int>>>.fillInRocks(): HashSet<Point> {
    val rocks = hashSetOf<Point>()
    forEach { path ->
        for (i in 0..path.size - 2) {
            val start = path[i]
            val end = path[i + 1]
            for (j in min(start.second, end.second)..max(start.second, end.second))
                rocks.add(Point(j, start.first))
            for (j in min(start.first, end.first)..max(start.first, end.first))
                rocks.add(Point(start.second, j))
        }
    }
    return rocks
}

fun HashSet<Point>.addFloor(): HashSet<Point> {
    val rocks = HashSet<Point>()
    rocks.addAll(this)
    val floorRow = rocks.maxOf { it.row } + 2
    val seconds = rocks.map { it.col }
    for (j in seconds.min() - 1000..seconds.max() + 1000) rocks.add(Point(floorRow, j))
    return rocks
}
