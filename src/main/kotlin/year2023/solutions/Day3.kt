package year2023.solutions

import utils.data.Point
import utils.setup.Day

fun main() = Day3().printDay()

class Day3 : Day(dayNumber = 3, year = 2023, useSampleInput = true) {
    private val special = inputList.mapIndexed { row, str ->
        str.mapIndexedNotNull { col, c ->
            if (!c.isDigit() && c != '.') Point(row, col) else null
        }
    }.flatten()

    private fun getNumbers(): List<Pair<Int, List<Point>>> {
        val numbers = mutableListOf<Pair<Int, List<Point>>>()
        var current = 0
        var start: Point? = null
        var symbolFound = false
        inputList
            .mapIndexed { row, s ->
                s.forEachIndexed { col, char ->
                    if (char.isDigit()) {
                        current = if (current == 0) {
                            start = Point(row, col)
                            char.digitToInt()
                        } else current * 10 + char.digitToInt()
                        if (
                            Point(row, col).allSides()
                                .map { inputList.getOrNull(it.row)?.getOrNull(it.col) }
                                .any { it?.isDigit() == false && it != '.' }
                        ) symbolFound = true
                    } else if (current != 0 && symbolFound) {
                        numbers.add(current to (start!!.col until col).map { Point(row, it) })
                        current = 0
                        symbolFound = false
                    } else {
                        current = 0
                    }
                }
            }
        return numbers
    }

    override fun partOne(): Any {
        return getNumbers().sumOf { it.first }
    }

    override fun partTwo(): Any {
        return ""
    }
}