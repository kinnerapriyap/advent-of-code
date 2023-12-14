package year2021.solutions

import utils.setup.Day
import kotlin.math.abs

class Day7 : Day(dayNumber = 7, year = 2021, useSampleInput = true) {
    private val input = inputString.split(",").mapNotNull { it.trim().toIntOrNull() }

    override fun partOne(): Any {
        return input.minOf { num -> input.sumOf { abs(num - it) } }
    }

    override fun partTwo(): Any {
        return (input.min()..input.max()).minOf { num ->
            input.sumOf {
                val x = abs(num - it)
                x * (x + 1) / 2
            }
        }
    }
}