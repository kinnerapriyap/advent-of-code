package year2024.solutions.kotlin

import utils.setup.Day

fun main() = Day25().printDay()

class Day25 : Day(dayNumber = 25, year = 2024, useSampleInput = false) {
    private val inputs = inputList.chunked(8).map { it.dropLast(1) }

    private enum class X { KEY, LOCK }

    override fun partOne(): Any {
        val counts = inputs.map { grid ->
            val columnCounts = MutableList(grid[0].length) { -1 }
            val topOrBottom = if (grid[0].all { it == '#' }) grid else grid.asReversed()
            val type = if (grid[0].all { it == '#' }) X.LOCK else X.KEY
            for (row in topOrBottom) {
                for ((colIndex, char) in row.withIndex()) {
                    if (char == '#') columnCounts[colIndex]++
                }
            }
            columnCounts to type
        }
        val keys = counts.filter { it.second == X.KEY }.map { it.first }
        val locks = counts.filter { it.second == X.LOCK }.map { it.first }
        var ans = 0
        for (k in keys) {
            for (l in locks) {
                if (k.zip(l).all { (a, b) -> a + b < 6 }) ans++
            }
        }
        return ans
    }
}