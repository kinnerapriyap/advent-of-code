package year2021.solutions

import utils.setup.Day
import java.awt.Point

class Day5 : Day(dayNumber = 5, year = 2021, useSampleInput = false) {
    private val input = inputList.map { str ->
        str.split("->").map { e ->
            e.split(",").mapNotNull { it.trim().toIntOrNull() }.let { Point(it[0], it[1]) }
        }
    }
    private val max: Int = input.map { it.map { p -> listOf(p.x, p.y) }.flatten() }.flatten().max() + 1
    private val ans = MutableList(max) { IntArray(max) { 0 } }

    override fun partOne(): Any {
        input.forEach { (start, end) ->
            if (start.x == end.x) {
                val range = if (start.y > end.y) (end.y..start.y) else (start.y..end.y)
                range.forEach { y -> ans[y][start.x]++ }
            } else if (start.y == end.y) {
                val range = if (start.x > end.x) (end.x..start.x) else (start.x..end.x)
                range.forEach { x -> ans[start.y][x]++ }
            }
        }
        return ans.fold(0) { acc, ints -> acc + ints.count { it > 1 } }
    }

    override fun partTwo(): Any {
        input.forEach { (start, end) ->
            if (start.x != end.x && start.y != end.y) {
                val xs = (if (start.x > end.x) start.x downTo end.x else start.x..end.x).toList()
                val ys = (if (start.y > end.y) start.y downTo end.y else start.y..end.y).toList()
                xs.indices.forEach { index ->
                    ans[ys[index]][xs[index]]++
                }
            }
        }
        return ans.fold(0) { acc, ints -> acc + ints.count { it > 1 } }
    }
}