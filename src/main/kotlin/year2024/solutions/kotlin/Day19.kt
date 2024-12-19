package year2024.solutions.kotlin

import utils.setup.Day

fun main() = Day19().printDay()

class Day19 : Day(dayNumber = 19, year = 2024, useSampleInput = false) {
    private val input = inputList[0].split(", ")
    private val patterns = inputList.subList(2, inputList.size)

    override fun partOne(): Any = patterns.count { canMakeDesignDp(it, input) != 0L }

    override fun partTwo() = patterns.sumOf { canMakeDesignDp(it, input) }

    fun canMakeDesign(design: String, availableDesigns: List<String>): Boolean {
        fun helper(remaining: String): Boolean {
            if (remaining.isEmpty()) return true
            for (design in availableDesigns) {
                if (remaining.startsWith(design)) {
                    if (helper(remaining.removePrefix(design))) return true
                }
            }
            return false
        }
        return helper(design)
    }

    fun canMakeDesignDp(design: String, availableDesigns: List<String>): Long {
        val dp = LongArray(design.length + 1) { 0L }
        dp[0] = 1L
        for (i in 1..design.length) {
            for (pattern in availableDesigns) {
                if (i >= pattern.length && design.substring(i - pattern.length, i) == pattern) {
                    dp[i] += dp[i - pattern.length]
                }
            }
        }
        return dp[design.length]
    }
}