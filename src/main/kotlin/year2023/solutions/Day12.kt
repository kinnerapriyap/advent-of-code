package year2023.solutions

import utils.setup.Day

fun main() = Day12().printDay()

class Day12 : Day(dayNumber = 12, year = 2023, useSampleInput = false) {
    private fun getInput(n: Int = 1) = inputList.map { str ->
        val (row, num) = str.split(" ")
        val numbers = num.split(",").mapNotNull { it.trim().toIntOrNull() }
        (List(n) { row }.joinToString("?") + '.') to
                List(n) { numbers }.flatten()
    }

    override fun partOne(): Any {
        return getInput().sumOf { dynamic(it.first, it.second) }
    }

    override fun partTwo(): Any {
        return getInput(5).sumOf { dynamic(it.first, it.second) }
    }

    private fun dynamic(row: String, numbers: List<Int>): Long {
        val size = row.length
        val dots = row.scan(0) { acc, c -> acc + (if (c == '.') 1 else 0) }

        fun fits(left: Int, right: Int) =
            right < size && dots[left] == dots[right] && row[right] != '#'

        val dp = LongArray(size + 1) { 0 }
        dp[0] = 1
        for (i in 1..size) {
            if (row[i - 1] != '#') dp[i] += dp[i - 1]
        }
        numbers.forEach { num ->
            for (i in size - num - 1 downTo 0) {
                if (fits(i, i + num)) dp[i + num + 1] = dp[i]
                else dp[i + num + 1] = 0
            }
            for (i in 0..num) dp[i] = 0
            for (i in 1..size) {
                if (row[i - 1] != '#') dp[i] += dp[i - 1]
            }
        }
        return dp.last()
    }
}
