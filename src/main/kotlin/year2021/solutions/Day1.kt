package year2021.solutions

import utils.setup.Day

class Day1 : Day(dayNumber = 1, year = 2021, useSampleInput = true) {
    private val input = inputList.map { it.toInt() }

    override fun partOne(): Any {
        return input.foldIndexed(0) { index, acc, i ->
            acc + if (i > (input.getOrNull(index - 1) ?: Int.MAX_VALUE)) 1 else 0
        }
    }

    override fun partTwo(): Any {
        var prev = Int.MAX_VALUE
        var count = 0
        for (i in 0..input.size - 3) {
            val new = input[i] + input[i + 1] + input[i + 2]
            if (new > prev) count++
            prev = new
        }
        return count
    }
}