package year2022.day06

import java.io.File

fun findMarker(line: String, noOfDistinctChar: Int): Int {
    var i = 0
    while (!line.substring(i, i + noOfDistinctChar).all(hashSetOf<Char>()::add)) i++
    return i + noOfDistinctChar
}

fun part1(line: String): Int = findMarker(line, 4)

fun part2(line: String): Int = findMarker(line, 14)

fun main() {
    val line = File("src/main/kotlin/year2022.day06/day6_input.txt")
        .bufferedReader()
        .readLines()
        .first()
    println(part1(line))
    println(part2(line))
}