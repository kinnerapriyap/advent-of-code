package year2021.solutions

import utils.data.Point
import utils.data.get
import utils.data.product
import utils.setup.Day

fun main() = Day9().printDay()

class Day9 : Day(dayNumber = 9, year = 2021, useSampleInput = false) {
    private val input = inputList.map { str -> str.map { it.digitToInt() } }
    private val lowPoints = input.mapIndexed { row, ints ->
        ints.mapIndexedNotNull { col, i ->
            val p = Point(row, col)
            if (p.neighbors().mapNotNull { input.get(it) }.all { it > i }) p
            else null
        }
    }.flatten()

    override fun partOne(): Any {
        return lowPoints.sumOf { (input.get(it) ?: 0) + 1 }
    }

    override fun partTwo(): Any {
        return lowPoints.map {
            val visited = hashSetOf<Point>()
            val queue = mutableListOf(it)
            while (queue.isNotEmpty()) {
                val n = queue.removeFirst()
                visited.add(n)
                n.neighbors().forEach N@{ p ->
                    if (input.get(p) == null || input.get(p) == 9 || p in visited) return@N
                    queue.add(p)
                }
            }
            visited.size
        }.sortedDescending().take(3).product()
    }
}