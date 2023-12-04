package utils.setup

import utils.data.Point

abstract class Day(
    private val dayNumber: Int,
    private val year: Int,
    private val useSampleInput: Boolean = true
) {
    val inputList: List<String> by lazy {
        InputReader.getInputAsList(dayNumber, year, useSampleInput)
    }
    val inputString: String by lazy {
        InputReader.getInputAsString(dayNumber, year, useSampleInput)
    }

    abstract fun partOne(): Any
    open fun partTwo(): Any = ""

    fun printDay() {
        val header = "Day $dayNumber, $year"
        val footer = "—".repeat(header.length)

        println(header)
        println("Part 1: ${partOne()}")
        println("Part 2: ${partTwo()}")
        println(footer)
    }

    fun getNumbersAndPositions(): List<Pair<Int, List<Point>>> {
        val colMax = inputList[0].length
        val numbers = mutableListOf<Pair<Int, List<Point>>>()
        var current = 0
        var start: Point? = null
        inputList
            .mapIndexed { row, s ->
                s.forEachIndexed { col, char ->
                    if (char.isDigit()) {
                        current = if (current == 0) {
                            start = Point(row, col)
                            char.digitToInt()
                        } else current * 10 + char.digitToInt()
                    } else if (current != 0) {
                        val c = if (col == 0 && start!!.col != 0) colMax else col
                        val r = if (col == 0 && start!!.col != 0) row - 1 else row
                        numbers.add(current to (start!!.col until c).map { Point(r, it) })
                        current = 0
                    }
                }
            }
        return numbers
    }
}