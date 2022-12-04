package day03

import java.io.File

fun part1(lines: List<String>): Int =
    lines.map { it.take(it.length / 2) to it.substring(it.length / 2) }
        .fold(0) { sum, (first, second) ->
            sum + first.toSet().intersect(second.toSet()).first().getValue()
        }

fun part2(lines: List<String>): Int =
    lines.chunked(3)
        .fold(0) { sum, (first, second, third) ->
            sum + first.toSet().intersect(second.toSet()).intersect(third.toSet()).first().getValue()
        }

fun Char.getValue(): Int =
    when (this) {
        in 'A'..'Z' -> this - 'A' + 27
        in 'a'..'z' -> this - 'a' + 1
        else -> 0
    }

fun main() {
    val lines = File("src/main/kotlin/day03/day3_input.txt")
        .bufferedReader()
        .readLines()
    println(part1(lines))
    println(part2(lines))
}