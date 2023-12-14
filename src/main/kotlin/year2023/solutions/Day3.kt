package year2023.solutions

import utils.data.Point
import utils.setup.Day

class Day3 : Day(dayNumber = 3, year = 2023, useSampleInput = false) {
    private fun special(onlyStar: Boolean = false): List<Point> =
        inputList.mapIndexed { row, str ->
            str.mapIndexedNotNull { col, c ->
                if (!c.isDigit() && c != '.') {
                    if ((onlyStar && c == '*') || !onlyStar) Point(row, col) else null
                } else null
            }
        }.flatten()

    private val colMax = inputList[0].length

    private fun getNumbers(): List<Pair<Int, List<Point>>> {
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

    override fun partOne(): Any {
        val special = special()
        return getNumbers()
            .filter { (_, points) -> points.any { it.allSides().any { x -> x in special } } }
            .sumOf { it.first }
    }

    override fun partTwo(): Any {
        return buildMap<Point, MutableList<Int>> {
            val stars = special(true).toSet()
            getNumbers().forEach { (num, points) ->
                points.map { it.allSides() }.flatten().intersect(stars).forEach {
                    put(it, getOrDefault(it, mutableListOf()).apply { add(num) })
                }
            }
        }.values.filter { it.size == 2 }.sumOf { it.reduce { acc, i -> acc * i } }
    }
}