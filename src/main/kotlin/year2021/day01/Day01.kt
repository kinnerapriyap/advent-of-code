package year2021.day01

import java.io.File

fun part1(lines: List<Int>): Int =
    lines.foldIndexed(0) { index, acc, i ->
        acc + if (i > (lines.getOrNull(index - 1) ?: Int.MAX_VALUE)) 1 else 0
    }

fun part2(lines: List<Int>): Int {
    var prev = Int.MAX_VALUE
    var count = 0
    for (i in 0..lines.size - 3) {
        val new = lines[i] + lines[i + 1] + lines[i + 2]
        if (new > prev) count++
        prev = new
    }
    return count
}

fun main() {
    val lines = File("src/main/kotlin/year2021/day01/day01_input.txt")
        .bufferedReader()
        .readLines()
        .map { it.toInt() }
    println(part1(lines))
    println(part2(lines))
}