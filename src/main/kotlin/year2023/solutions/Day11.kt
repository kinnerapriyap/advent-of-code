package year2023.solutions

import utils.data.Point
import utils.setup.Day
import kotlin.math.abs

class Day11 : Day(dayNumber = 11, year = 2023, useSampleInput = false) {
    override fun partOne(): Any {
        return getTotalDistance()
    }

    override fun partTwo(): Any {
        return getTotalDistance(999999)
    }

    private fun getTotalDistance(extra: Int = 1): Long {
        val input = inputList.map { str -> str.toMutableList() }.toMutableList()
        val emptyRows = input.indices.filter { row -> input[row].all { it == '.' } }
        val emptyColumns = input[0].indices.filter { col -> input.indices.all { input[it][col] == '.' } }
        val hashes = input.mapIndexed { row, chars ->
            chars.mapIndexed { col, c -> Point(row, col) to c }
        }.flatten().filter { it.second == '#' }.map { it.first }

        var ans = 0L
        for (i in hashes.indices) {
            for (j in i + 1..hashes.lastIndex) {
                val rows =
                    (if (hashes[i].row > hashes[j].row) hashes[j].row..hashes[i].row
                    else hashes[i].row..hashes[j].row).intersect(emptyRows.toSet()).count()
                val cols =
                    (if (hashes[i].col > hashes[j].col) hashes[j].col..hashes[i].col
                    else hashes[i].col..hashes[j].col).intersect(emptyColumns.toSet()).count()
                val e = (rows * extra) + (cols * extra)
                ans += abs(hashes[i].row - hashes[j].row) + abs(hashes[i].col - hashes[j].col) + e
            }
        }
        return ans
    }
}
