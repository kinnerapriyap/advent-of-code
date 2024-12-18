package year2024.solutions.kotlin

import utils.data.Point
import utils.data.findShortestPath
import utils.data.get
import utils.setup.Day

fun main() = Day18().printDay()

private const val MAX = 7

class Day18 : Day(dayNumber = 18, year = 2024, useSampleInput = false) {
    private val input = inputList.map { str -> str.split(",").let { Point(it[0].toInt(), it[1].toInt()) } }
    private val grid = Array(MAX) { Array(MAX) { '.' } }

    override fun partOne(): Any {
        input.take(12).forEach { grid[it.row][it.col] = '#' }
        val x = findShortestPath(
            Point(0, 0),
            Point(MAX - 1, MAX - 1),
            { point -> point.neighbors().filter { grid.get(it) != null && grid.get(it) != '#' } }
        )
        x.getPath().forEach { grid[it.row][it.col] = 'O' }
        return x.getPath().count() - 1
    }

    override fun partTwo(): Any {
        input.take(12).forEach { grid[it.row][it.col] = '#' }
        input.subList(13, input.size).forEachIndexed { index, (row, col) ->
            grid[row][col] = '#'
            val x = findShortestPath(
                Point(0, 0),
                Point(MAX - 1, MAX - 1),
                { point -> point.neighbors().filter { grid.get(it) != null && grid.get(it) != '#' } }
            )
            try {
                x.getPath()
            } catch (_: Exception) {
                return "$row,$col"
            }
        }
        return 0
    }
}