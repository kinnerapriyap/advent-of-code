package year2023.solutions

import utils.data.Point
import utils.data.get
import utils.setup.Day

fun main() {
    Day10().printDay()
}

class Day10 : Day(dayNumber = 10, year = 2023, useSampleInput = false) {
    private val basic = inputList.map { it.toList() }
    private val start = basic.indexOfFirst { 'S' in it }.let { Point(it, basic[it].indexOf('S')) }
    private val path = mutableListOf(start)

    override fun partOne(): Any {
        //val next = start.canContinueWith()
        var prev = start
        var curr = start.down()
        var steps = 1
        while (curr != start) {
            path.add(curr)
            val temp = curr
            curr = curr.canContinueWith().first { it != prev }
            prev = temp
            steps++
        }
        return steps / 2
    }

    override fun partTwo(): Any {
        return inputList.mapIndexedNotNull { row, s ->
            if (row !in path.minOf { it.row }..path.maxOf { it.row }) return@mapIndexedNotNull null
            val new = s.slice(path.minOf { it.col }..path.maxOf { it.col })
                .replace("-", "")
                .replace("F7", "")
                .replace("LJ", "")
                .replace("L7", "|")
                .replace("FJ", "|")
            new.mapIndexed { index, segment ->
                if (segment != '|') {
                    new.take(index).count { it == '|' }.let { if (it % 2 == 0) ' ' else '.' }
                } else segment
            }.joinToString("")
        }.sumOf { r -> r.count { it == '.' } }
    }

    private fun Point.canContinueWith(char: Char? = null): List<Point> =
        when (char ?: basic.get(this)) {
            '|' -> listOf(up(), down())
            '-' -> listOf(right(), left())
            'L' -> listOf(up(), right())
            'J' -> listOf(up(), left())
            '7' -> listOf(left(), down())
            'F' -> listOf(right(), down())
            'S' -> listOf(right(), down(), up(), left())
            else -> listOf()
        }.filter { it.row in basic.indices && it.col in basic[0].indices }
}
