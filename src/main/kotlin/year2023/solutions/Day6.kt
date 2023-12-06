package year2023.solutions

import utils.data.product
import utils.setup.Day

fun main() = Day6().printDay()

class Day6 : Day(dayNumber = 6, year = 2023, useSampleInput = false) {
    private val input = inputList.map { str ->
        str.split(":")[1].split(" ").mapNotNull { it.toIntOrNull() }
    }

    override fun partOne(): Any {
        return input[0].mapIndexed { index, time ->
            (1 until time).fold(0) { acc, currTime ->
                if (currTime * (time - currTime) > input[1][index]) acc + 1 else acc
            }
        }.product()
    }

    override fun partTwo(): Any {
        val (time, distance) = input.mapNotNull { it.joinToString("").toLongOrNull() }
        return (1 until time).fold(0) { acc, j ->
            if (j * (time - j) > distance) acc + 1 else acc
        }
    }
}