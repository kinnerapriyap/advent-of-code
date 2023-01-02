package year2022.day10

import java.io.File

fun part1(lines: List<String>): Int =
    getCycles(lines).foldIndexed(0) { index, sum, cycle ->
        if ((index - 18) % 40 == 0) sum + (cycle * (index + 2)) else sum
    }

fun part2(lines: List<String>) {
    var sprite = listOf(0, 1, 2)
    getCycles(lines).forEachIndexed { index, s ->
        val indexInRow = index % 40
        if (sprite.contains(indexInRow)) print("X ")
        else print(". ")
        sprite = listOf(s - 1, s, s + 1)
        if ((index - 39) % 40 == 0) println()
    }
}

fun getCycles(lines: List<String>) =
    mutableListOf<Int>().apply {
        var signal = 1
        lines.forEach { element ->
            if (element.startsWith("addx")) {
                add(signal)
                signal += element.substringAfterLast(" ").toInt()
                add(signal)
            } else add(signal)
        }
    }

fun main() {
    val lines = File("src/main/kotlin/year2022.day10/day10_input.txt")
        .bufferedReader()
        .readLines()
    println(part1(lines))
    part2(lines)
}