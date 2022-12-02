package day01

import java.io.File

// Problem: https://adventofcode.com/2022/day/1

fun part1(): Int = calculateTotals().first()

fun part2(): Int = calculateTotals().take(3).sum()

fun calculateTotals(): List<Int> =
    mutableListOf<Int>().apply {
        File("/Users/kinnera/kinnerapriyap/advent-of-code-2022/src/main/kotlin/day01/day1_input.txt")
            .bufferedReader()
            .readLines()
            .fold(0) { sum, element ->
                if (element.isBlank()) {
                    add(sum)
                    0
                } else sum + element.toInt()
            }
    }.sortedDescending()

fun main() {
    println(part1())
    println(part2())
}