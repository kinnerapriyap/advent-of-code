package year2023.solutions

import utils.data.Point
import utils.data.get
import utils.setup.Day

class Day21 : Day(dayNumber = 21, year = 2023, useSampleInput = false) {
    private val input = inputList.mapIndexed { row, s -> s.mapIndexed { col, c -> Point(row, col) to c } }
    private val sPoint = input.flatten().find { it.second == 'S' }!!.first
    private val size = input.size
    private val colSize = input[0].size

    override fun partOne(): Any {
        var stepPoints = setOf(sPoint)
        var steps = 64
        while (steps-- > 0) {
            val newStepPoints = mutableSetOf<Point>()
            stepPoints.forEach { next ->
                next.neighbors()
                    .filter { input.get(it)?.second != '#' }
                    .forEach { neighbor -> newStepPoints.add(neighbor) }
            }
            stepPoints = newStepPoints
        }
        return stepPoints.size
    }

    override fun partTwo(): Any {
        val ans = mutableListOf<Int>()
        var stepPoints = setOf(sPoint)
        var steps = 0
        while (steps++ < 350) {
            val newStepPoints = mutableSetOf<Point>()
            stepPoints.forEach { next ->
                next.neighbors()
                    .filter { getOutOfBounds(it) != '#' }
                    .forEach { neighbor -> newStepPoints.add(neighbor) }
            }
            if (steps % 131 == 65) ans.add(newStepPoints.size)
            stepPoints = newStepPoints
        }
        // Remainder is 26501365 % 131 = 65 so
        // [ans(65)=3874, ans(65+131)=34549, ans(65+2*131)=95798]
        // These answers are quadratic, so the equation that fits these values is
        // 3874 + 15388 x + 15287 x^2
        // Plug in x in the above equation as 26501365 / 131 = 202300
        return ans
    }

    private fun getOutOfBounds(point: Point): Char {
        val newRow = if (point.row >= 0) point.row % size else (size - (-point.row % size)) % size
        val newCol = if (point.col >= 0) point.col % colSize else (colSize - (-point.col % colSize)) % colSize
        return input.get(newRow, newCol)!!.second
    }
}