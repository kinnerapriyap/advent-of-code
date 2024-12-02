package year2024.solutions.kotlin

import utils.setup.Day
import kotlin.math.abs

fun main() = Day2().printDay()

class Day2 : Day(dayNumber = 2, year = 2024, useSampleInput = false) {

    private val input = inputList.map { str ->
        str.split(" ").map { it.toInt() }
    }

    override fun partOne(): Any {
        var count = 0
        input.forEach { level ->
            if (isSafeReport(level)) count++
        }
        return count
    }

    override fun partTwo(): Any {
        var count = 0
        for (level in input) {
            if (isSafeReport(level)) {
                count++
                continue
            } else {
                for (i in level.indices) {
                    val modified = level.toMutableList().apply { removeAt(i) }
                    if (isSafeReport(modified)) {
                        count++
                        break
                    }
                }
            }
        }
        return count
    }

    private fun isSafeReport(level: List<Int>): Boolean {
        if (level.sorted() == level || level.sortedDescending() == level) {
            for (i in 1..level.lastIndex) {
                if (abs(level[i] - level[i - 1]) !in 1..3) return false
            }
            return true
        }
        return false
    }
}