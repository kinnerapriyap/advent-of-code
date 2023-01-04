package year2021.day03

import java.io.File
import kotlin.text.StringBuilder

fun part1(lines: List<IntArray>): Int {
    val gamma = StringBuilder()
    val epsilon = StringBuilder()
    val half = lines.size / 2
    for (i in lines.first().indices) {
        val zeros = lines.map { it[i] }.count { it == 0 }
        gamma.append(if (zeros > half) 0 else 1)
        epsilon.append(if (zeros > half) 1 else 0)
    }
    return gamma.toString().toInt(2) * epsilon.toString().toInt(2)
}

fun part2(lines: List<List<Int>>): Int {
    var oxygen = lines
    var co2 = lines
    for (i in lines.first().indices) {
        val majority = if (oxygen.map { it[i] }.count { it == 0 } > oxygen.size / 2) 0 else 1
        oxygen = oxygen.filter { it[i] == majority }
        if (oxygen.size == 1) break
    }
    for (i in lines.first().indices) {
        val majority = if (co2.map { it[i] }.count { it == 0 } > co2.size / 2) 0 else 1
        co2 = co2.filter { it[i] != majority }
        if (co2.size == 1) break
    }
    println(oxygen)
    println(co2)
    return oxygen.first().joinToString("").toInt(2) * co2.first().joinToString("").toInt(2)
}

fun main() {
    val lines = File("src/main/kotlin/year2021/day03/day03_input.txt")
        .bufferedReader()
        .readLines()
        .map { line ->
            line.toCharArray().map { it.digitToInt() }
        }
    println(part1(lines.map { it.toIntArray() }))
    println(part2(lines))
}