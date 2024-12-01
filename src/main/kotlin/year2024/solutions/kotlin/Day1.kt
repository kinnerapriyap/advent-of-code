package year2024.solutions.kotlin

import utils.setup.Day
import kotlin.math.abs

fun main() = Day1().printDay()

class Day1 : Day(dayNumber = 1, year = 2024, useSampleInput = true) {
    private val input = inputList.map { str ->
        val (one, two) = str.split("   ")
        one.toInt() to two.toInt()
    }
    private val first = input.map { it.first }.sorted()
    private val second = input.map { it.second }.sorted()

    override fun partOne() = first.zip(second).sumOf { abs(it.first - it.second) }

    override fun partTwo(): Any = first.sumOf { no -> second.count { it == no } * no }
}