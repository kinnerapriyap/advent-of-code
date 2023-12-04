package year2023.solutions

import utils.setup.Day
import kotlin.math.pow

fun main() = Day4().printDay()

class Day4 : Day(dayNumber = 4, year = 2023, useSampleInput = false) {
    private val input = inputList.map { str ->
        val (number, rest) = str.split(": ")
        val (winning, have) = rest.split(" | ")
            .map { s -> s.split(" ").mapNotNull { it.trim().toIntOrNull() } }
        number to winning.intersect(have.toSet()).count()
    }

    override fun partOne(): Any {
        return input.sumOf { 2.0.pow((it.second - 1).toDouble()).toInt() }
    }

    override fun partTwo(): Any {
        return IntArray(input.size) { 1 }.apply {
            for (i in input.indices) repeat(input[i].second) { this[i + it + 1] += this[i] }
        }.sum()
    }
}
