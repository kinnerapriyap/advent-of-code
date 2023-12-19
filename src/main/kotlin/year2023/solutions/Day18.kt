package year2023.solutions

import utils.data.DirectionOrtho
import utils.data.Point
import utils.data.move
import utils.setup.Day
import kotlin.math.abs

class Day18 : Day(dayNumber = 18, year = 2023, useSampleInput = false) {
    data class TD(val dir: DirectionOrtho, val steps: Int, val colorDir: DirectionOrtho, val colorSteps: Int)

    private val input = inputList.map { str ->
        val (dir, steps, colorStr) = str.split(" ")
        val color = colorStr.removePrefix("(#").removeSuffix(")")
        TD(
            when (dir[0]) {
                'R' -> DirectionOrtho.RIGHT
                'L' -> DirectionOrtho.LEFT
                'U' -> DirectionOrtho.UP
                else -> DirectionOrtho.DOWN
            },
            steps.toInt(),
            when (color.last().digitToInt()) {
                0 -> DirectionOrtho.RIGHT
                1 -> DirectionOrtho.DOWN
                2 -> DirectionOrtho.LEFT
                3 -> DirectionOrtho.UP
                else -> error("Wrong dir")
            },
            color.substring(0, 5).toInt(16)
        )
    }

    override fun partOne(): Any {
        var start = Point(0, 0)
        val vertices = mutableListOf(start)
        input.forEach { (dir, steps, _, _) ->
            start = dir.move(start, steps).also { vertices.add(it) }
        }
        return abs(vertices.getArea()) + input.sumOf { it.steps } / 2 + 1
    }

    override fun partTwo(): Any {
        var start = Point(0, 0)
        val vertices = mutableListOf(start)
        input.forEach { (_, _, colorDir, colorSteps) ->
            start = colorDir.move(start, colorSteps).also { vertices.add(it) }
        }
        return abs(vertices.getArea()) + input.sumOf { it.colorSteps } / 2 + 1
    }

    private fun List<Point>.getArea() =
        zipWithNext { (r1, c1), (r2, c2) -> (r1.toLong() * c2 - c1 * r2.toLong()) }.sum() / 2L
}