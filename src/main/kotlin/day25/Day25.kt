package day25

import java.io.File
import kotlin.math.pow

fun part1(lines: List<Long>): String = lines.sum().toSnafu()

fun part2(): String = "did-do-done!"

fun main() {
    val lines = File("src/main/kotlin/day25/day25_input.txt")
        .bufferedReader()
        .readLines()
        .map { it.toCharArray().fromSnafu() }
    println(part1(lines))
    println(part2())
}

fun CharArray.fromSnafu(): Long =
    foldRightIndexed(0.0) { index, c, acc ->
        val digit = when {
            c.isDigit() -> c.digitToInt()
            c == '-' -> -1.0
            c == '=' -> -2.0
            else -> 0.0
        }
        acc + 5.0.pow(size - index - 1) * digit.toDouble()
    }.toLong()

fun Long.toSnafu(): String {
    var x = this
    var i = 29
    val ans = IntArray(30) { 0 }
    while (x > 0) {
        val rem = x % 5L
        if (ans[i] + rem > 2) {
            ans[i] += (rem - 5).toInt()
            ans[i - 1] += 1
        } else ans[i] += rem.toInt()
        x /= 5
        i--
    }
    return ans.map { if (it == -1) '-' else if (it == -2) '=' else it.toString()[0] }.joinToString("")
}