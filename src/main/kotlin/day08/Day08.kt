package day08

import java.io.File

fun part1(lines: List<List<Int>>): Int =
    lines.foldIndexed(0) { index, sum, row ->
        sum + row.foldIndexed(0) { rowIndex, rowSum, tree ->
            val onEdge = index == lines.size - 1 || index == 0 || rowIndex == 0 || rowIndex == row.size - 1
            val visibleOnTop = mutableListOf<Int>().apply {
                for (i in 0 until index) add(lines[i][rowIndex])
            }.all { it < tree }
            val visibleOnBottom = mutableListOf<Int>().apply {
                for (i in index + 1 until lines.size) add(lines[i][rowIndex])
            }.all { it < tree }
            val visibleOnLeft = row.subList(0, rowIndex).all { it < tree }
            val visibleOnRight = row.subList(rowIndex + 1, row.size).all { it < tree }
            if (onEdge || visibleOnTop || visibleOnBottom || visibleOnLeft || visibleOnRight)
                rowSum + 1
            else rowSum
        }
    }

fun part2(lines: List<List<Int>>): Int =
    lines.foldIndexed(0) { index, sum, row ->
        val rowMax = row.foldIndexed(0) { rowIndex, rowMax, tree ->
            var scenicScore = 1
            if (index == lines.size - 1 || index == 0 || rowIndex == 0 || rowIndex == row.size - 1)
                scenicScore = 0
            else {
                val top = mutableListOf<Int>().apply {
                    for (i in 0 until index) add(lines[i][rowIndex])
                }.indexOfLast { it >= tree }
                scenicScore *= if (top == -1) index else index - top

                val bottom = mutableListOf<Int>().apply {
                    for (i in index + 1 until lines.size) add(lines[i][rowIndex])
                }.indexOfFirst { it >= tree }
                scenicScore *= if (bottom == -1) lines.size - index - 1 else bottom + 1

                val left = row.subList(0, rowIndex).indexOfLast { it >= tree }
                scenicScore *= if (left == -1) rowIndex else rowIndex - left

                val right = row.subList(rowIndex + 1, row.size).indexOfFirst { it >= tree }
                scenicScore *= if (right == -1) row.size - rowIndex - 1 else right + 1
            }
            maxOf(rowMax, scenicScore)
        }
        maxOf(sum, rowMax)
    }

fun main() {
    val lines = File("src/main/kotlin/day08/day8_input.txt")
        .bufferedReader()
        .readLines()
        .map { it.chunked(1).map { c -> c.toInt() } }
    println(part1(lines))
    println(part2(lines))
}