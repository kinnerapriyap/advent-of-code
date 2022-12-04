package day04

import java.io.File

fun part1(lines: List<List<IntRange>>): Int =
    lines.fold(0) { sum, (first, second) ->
        val common = first.intersect(second)
        sum + if (common == first.toSet() || common == second.toSet()) 1 else 0
    }

fun part2(lines: List<List<IntRange>>): Int =
    lines.fold(0) { sum, (first, second) ->
        sum + if (first.intersect(second).isNotEmpty()) 1 else 0
    }

fun main() {
    val lines = File("src/main/kotlin/day04/day4_input.txt")
        .bufferedReader()
        .readLines()
        .map {
            it.split(",", limit = 2)
                .map { sections ->
                    val (start, end) = sections.split("-", limit = 2)
                    IntRange(start.toInt(), end.toInt())
                }
        }
    println(part1(lines))
    println(part2(lines))
}