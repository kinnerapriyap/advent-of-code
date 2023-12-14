package year2021.solutions

import utils.setup.Day

class Day3 : Day(dayNumber = 3, year = 2021, useSampleInput = true) {
    private val input = inputList.map { line ->
        line.toCharArray().map { it.digitToInt() }
    }

    override fun partOne(): Any {
        val gamma = StringBuilder()
        val epsilon = StringBuilder()
        val half = input.size / 2
        for (i in input.first().indices) {
            val zeros = input.map { it[i] }.count { it == 0 }
            gamma.append(if (zeros > half) 0 else 1)
            epsilon.append(if (zeros > half) 1 else 0)
        }
        return gamma.toString().toInt(2) * epsilon.toString().toInt(2)
    }

    override fun partTwo(): Any {
        var oxygen = input
        var co2 = input
        for (i in input.first().indices) {
            val majority = if (oxygen.map { it[i] }.count { it == 0 } > oxygen.size / 2) 0 else 1
            oxygen = oxygen.filter { it[i] == majority }
            if (oxygen.size == 1) break
        }
        for (i in input.first().indices) {
            val majority = if (co2.map { it[i] }.count { it == 0 } > co2.size / 2) 0 else 1
            co2 = co2.filter { it[i] != majority }
            if (co2.size == 1) break
        }
        return oxygen.first().joinToString("").toInt(2) * co2.first().joinToString("").toInt(2)
    }
}