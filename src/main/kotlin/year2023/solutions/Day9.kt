package year2023.solutions

import utils.setup.Day

fun main() = Day9().printDay()

class Day9 : Day(dayNumber = 9, year = 2023, useSampleInput = false) {
    private val input = inputList.map { str ->
        val line = str.split(" ").mapNotNull { it.trim().toIntOrNull() }
        mutableListOf(line).apply {
            while (last().any { it != 0 }) {
                add(List(last().size - 1) { last()[it + 1] - last()[it] })
            }
        }
    }

    override fun partOne(): Any {
        return input.fold(0) { acc, list ->
            acc + list.foldRight(0) { ints, a -> a + ints.last() }
        }
    }

    override fun partTwo(): Any {
        return input.fold(0) { acc, list ->
            acc + list.foldRight(0) { ints, a -> ints.first() - a }
        }
    }
}