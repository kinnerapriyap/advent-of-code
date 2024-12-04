package year2024.solutions.kotlin

import utils.data.Point
import utils.data.diagonalsDelta
import utils.data.get
import utils.data.neighborsDelta
import utils.setup.Day

fun main() = Day4().printDay()

class Day4 : Day(dayNumber = 4, year = 2024, useSampleInput = true) {
    private val input = inputList.map { str -> str.toCharArray() }

    override fun partOne(): Any {
        var ans = 0
        input.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, c ->
                for ((dr, dc) in neighborsDelta() + diagonalsDelta()) {
                    val word = "XMAS"
                    var (r, c) = row to col
                    var x = true
                    for (i in 0 until 4) {
                        if (input.get(Point(r, c)) != word[i]) {
                            x = false
                            break
                        }
                        r += dr
                        c += dc
                    }
                    if (x) ans++
                }
            }
        }
        return ans
    }

    override fun partTwo(): Any {
        var ans = 0
        input.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, c ->
                if (c == 'A') {
                    val one = setOf(Point(row - 1, col + 1), Point(row + 1, col - 1))
                        .mapNotNull { input.get(it) }.toSet()
                    val two = setOf(Point(row + 1, col + 1), Point(row - 1, col - 1))
                        .mapNotNull { input.get(it) }.toSet()
                    if (one == setOf('M', 'S') && two == setOf('M', 'S')) ans++
                }
            }
        }
        return ans
    }
}