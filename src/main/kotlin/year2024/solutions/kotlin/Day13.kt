package year2024.solutions.kotlin

import utils.data.Point
import utils.setup.Day

fun main() = Day13().printDay()

data class Thirteen(val a: Point, val b: Point, val total: Point)

class Day13 : Day(dayNumber = 13, year = 2024, useSampleInput = false) {
    private val input = inputList.chunked(4).map {
        val (ax, ay) = Regex("""X\+(\d+), Y\+(\d+)""").find(it[0])!!.destructured
        val (bx, by) = Regex("""X\+(\d+), Y\+(\d+)""").find(it[1])!!.destructured
        val (x, y) = Regex("""X=(\d+), Y=(\d+)""").find(it[2])!!.destructured
        Thirteen(Point(ax.toInt(), ay.toInt()), Point(bx.toInt(), by.toInt()), Point(x.toInt(), y.toInt()))
    }

    override fun partOne(): Any {
        var ans = 0
        input.forEach {
            val (a, b, total) = it
            val determinant = a.row * b.col - a.col * b.row
            val i = (total.row * b.col - total.col * b.row).takeIf { determinant != 0 && (it % determinant == 0) }
                ?.let { it / determinant }
            val j = (a.row * total.col - a.col * total.row).takeIf { determinant != 0 && (it % determinant == 0) }
                ?.let { it / determinant }
            if (i != null && j != null) ans += 3 * i + j
        }
        return ans
    }

    override fun partTwo(): Any {
        var ans = 0L
        input.forEach {
            val (a, b, total) = it
            val tx = total.row.toLong() + 10000000000000L
            val ty = total.col.toLong() + 10000000000000L
            val determinant = (a.row * b.col - a.col * b.row).toLong()
            val i = (tx * b.col - ty * b.row).takeIf { determinant != 0L && (it % determinant == 0L) }
                ?.let { it / determinant }
            val j = (a.row * ty - a.col * tx).takeIf { determinant != 0L && (it % determinant == 0L) }
                ?.let { it / determinant }
            if (i != null && j != null) ans += 3 * i + j
        }
        return ans
    }
}