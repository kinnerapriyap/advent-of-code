package year2023.solutions

import utils.data.DirectionOrtho
import utils.data.Point
import utils.data.get
import utils.data.horizontalOrthoDirections
import utils.data.move
import utils.data.verticalOrthoDirections
import utils.setup.Day

class Day16 : Day(dayNumber = 16, year = 2023, useSampleInput = false) {
    private val input = inputList.map { str -> str.toList() }

    override fun partOne(): Any {
        return getEnergizedTiles(Point(0, 0), DirectionOrtho.RIGHT)
    }

    override fun partTwo(): Any {
        return maxOf(
            List(input[0].size) { col -> getEnergizedTiles(Point(0, col), DirectionOrtho.DOWN) }.max(),
            List(input[0].size) { col -> getEnergizedTiles(Point(input.lastIndex, col), DirectionOrtho.UP) }.max(),
            List(input.size) { row -> getEnergizedTiles(Point(row, 0), DirectionOrtho.RIGHT) }.max(),
            List(input.size) { row -> getEnergizedTiles(Point(row, input[0].lastIndex), DirectionOrtho.LEFT) }.max()
        )
    }

    private fun getEnergizedTiles(start: Point, direction: DirectionOrtho): Int {
        val curr = mutableListOf<Pair<Point, DirectionOrtho>>()
        val o = Array(inputList.size) { Array(inputList[0].length) { hashSetOf<DirectionOrtho>() } }
        input[start.row][start.col].whatHappens(direction).forEach { newDir ->
            if (newDir !in o[start.row][start.col]) {
                curr.add(start to newDir)
                o[start.row][start.col] = o[start.row][start.col].apply { add(newDir) }
            }
        }
        while (curr.isNotEmpty()) {
            val (p, dir) = curr.removeFirst()
            dir.move(p).let { newPoint ->
                input.get(newPoint)?.whatHappens(dir)?.forEach { newDir ->
                    if (newDir !in o[newPoint.row][newPoint.col]) {
                        curr.add(newPoint to newDir)
                        o[newPoint.row][newPoint.col] = o[newPoint.row][newPoint.col].apply { add(newDir) }
                    }
                }
            }
        }
        return o.sumOf { r -> r.count { it.size > 0 } }
    }

    private fun Char.whatHappens(curr: DirectionOrtho): List<DirectionOrtho> =
        when (this) {
            '.' -> listOf(curr)
            '|' -> if (curr in verticalOrthoDirections()) listOf(curr) else verticalOrthoDirections()
            '-' -> if (curr in horizontalOrthoDirections()) listOf(curr) else horizontalOrthoDirections()
            '\\' -> when (curr) {
                DirectionOrtho.UP -> listOf(DirectionOrtho.LEFT)
                DirectionOrtho.DOWN -> listOf(DirectionOrtho.RIGHT)
                DirectionOrtho.RIGHT -> listOf(DirectionOrtho.DOWN)
                DirectionOrtho.LEFT -> listOf(DirectionOrtho.UP)
            }

            '/' -> when (curr) {
                DirectionOrtho.UP -> listOf(DirectionOrtho.RIGHT)
                DirectionOrtho.DOWN -> listOf(DirectionOrtho.LEFT)
                DirectionOrtho.RIGHT -> listOf(DirectionOrtho.UP)
                DirectionOrtho.LEFT -> listOf(DirectionOrtho.DOWN)
            }

            else -> listOf()
        }
}