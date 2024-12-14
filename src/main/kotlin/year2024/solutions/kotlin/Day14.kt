package year2024.solutions.kotlin

import utils.data.product
import utils.setup.Day

fun main() = Day14().printDay()

private const val MAX_X = 101 //11
private const val MAX_Y = 103 //7

class Day14 : Day(dayNumber = 14, year = 2024, useSampleInput = true) {
    private val input = inputList.map { str ->
        str.split(" ").let {
            val p = it[0].substring(2).split(",").map(String::toInt)
            val v = it[1].substring(2).split(",").map(String::toInt)
            p to v
        }
    }

    override fun partOne(): Any {
        var new = input.map { it.first }.toMutableList()
        repeat(100) {
            input.forEachIndexed { index, (_, v) ->
                val newx = (new[index][0] + v[0]).let {
                    var x = it
                    while (x > MAX_X - 1) x -= MAX_X
                    while (x < 0) x += MAX_X
                    x
                }
                val newy = (new[index][1] + v[1]).let {
                    var y = it
                    while (y > MAX_Y - 1) y -= MAX_Y
                    while (y < 0) y += MAX_Y
                    y
                }
                new[index] = listOf(newx, newy)
            }
        }
        val grid = Array(MAX_Y) { IntArray(MAX_X) }
        new.forEach { (x, y) -> grid[y][x]++ }

        val quadrants = Array(4) { Array(MAX_Y / 2) { IntArray(MAX_X / 2) } }
        new.forEach { (x, y) ->
            val quadrant = when {
                x < MAX_X / 2 && y < MAX_Y / 2 -> 0
                x > MAX_X / 2 && y < MAX_Y / 2 -> 1
                x < MAX_X / 2 && y > MAX_Y / 2 -> 2
                x > MAX_X / 2 && y > MAX_Y / 2 -> 3
                else -> 4
            }
            val newX = x % (MAX_X / 2)
            val newY = y % (MAX_Y / 2)
            if (quadrant != 4) quadrants[quadrant][newY][newX]++
        }
        val quadrantSums = quadrants.map { quadrant ->
            quadrant.sumOf { row -> row.sum() }
        }

        return quadrantSums.product()
    }

    override fun partTwo(): Any {
        var new = input.map { it.first }.toMutableList()
        repeat(7093) {
            input.forEachIndexed { index, (_, v) ->
                val newx = (new[index][0] + v[0]).let {
                    var x = it
                    while (x > MAX_X - 1) x -= MAX_X
                    while (x < 0) x += MAX_X
                    x
                }
                val newy = (new[index][1] + v[1]).let {
                    var y = it
                    while (y > MAX_Y - 1) y -= MAX_Y
                    while (y < 0) y += MAX_Y
                    y
                }
                new[index] = listOf(newx, newy)
            }
        }
        val grid = Array(MAX_Y) { IntArray(MAX_X) }
        new.forEach { (x, y) -> grid[y][x]++ }
        grid.forEach { row -> println(row.joinToString(" ") { if (it == 0) "." else it.toString() }) }
        return 0
    }
}