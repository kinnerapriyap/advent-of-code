package year2024.solutions.kotlin

import utils.data.Point
import utils.data.get
import utils.setup.Day
import kotlin.text.get

fun main() = Day10().printDay()

class Day10 : Day(dayNumber = 10, year = 2024, useSampleInput = false) {
    private val input = inputList.map { str -> str.split("").mapNotNull { it.toIntOrNull() } }

    val zeros = mutableListOf<Point>()
    init {
        input.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, char ->
                if (char == 0) zeros.add(Point(row, col))
            }
        }
    }

    override fun partOne(): Any {
        var c = 0
        zeros.forEach { point ->
            var curr = 0
            var checking = setOf<Point>(point)
            while (curr != 9 && checking.isNotEmpty()) {
                val new = mutableSetOf<Point>()
                checking.forEach { check ->
                    val neighbors = check.neighbors().filter { input.get(it) == curr + 1 }
                    new.addAll(neighbors.toSet())
                }
                checking = new
                curr++
            }
            c += checking.count()
        }
        return c
    }

    override fun partTwo(): Any {
        fun getPaths(point: Point, curr: Int): List<List<Point>> {
            if (curr == 9) return listOf(listOf(point))
            val neighbors = point.neighbors().filter { input.get(it) == curr + 1 }
            val paths = mutableListOf<List<Point>>()
            neighbors.forEach { neighbor ->
                getPaths(neighbor, curr + 1).forEach { subPath ->
                    paths.add(listOf(point) + subPath)
                }
            }
            return paths
        }
        val allPaths = mutableListOf<List<Point>>()
        zeros.forEach { point -> allPaths.addAll(getPaths(point, 0)) }
        return allPaths.count()
    }
}