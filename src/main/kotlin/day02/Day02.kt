package day02

import java.io.File

val winCombos = mapOf("A" to "Y", "B" to "Z", "C" to "X")
val drawCombos = mapOf("A" to "X", "B" to "Y", "C" to "Z")
val loseCombos = mapOf("A" to "Z", "B" to "X", "C" to "Y")
val playScores = mapOf("X" to 1, "Y" to 2, "Z" to 3)

fun part1(lines: List<List<String>>): Int =
    lines.fold(0) { sum, (opponentPlay, yourPlay) ->
        val roundScore = when (yourPlay) {
            winCombos[opponentPlay] -> 6
            drawCombos[opponentPlay] -> 3
            else -> 0
        }
        sum + playScores.getOrDefault(yourPlay, 0) + roundScore
    }

fun part2(lines: List<List<String>>): Int =
    lines.fold(0) { sum, (opponentPlay, outcome) ->
        sum + when (outcome) {
            "Z" -> 6 + playScores.getOrDefault(winCombos[opponentPlay], 0)
            "Y" -> 3 + playScores.getOrDefault(drawCombos[opponentPlay], 0)
            else -> 0 + playScores.getOrDefault(loseCombos[opponentPlay], 0)
        }
    }

fun main() {
    val lines = File("src/main/kotlin/day02/day2_input.txt")
        .bufferedReader()
        .readLines()
        .map { it.split(" ", limit = 2) }
    println(part1(lines))
    println(part2(lines))
}