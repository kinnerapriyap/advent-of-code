package day20

import java.io.File

fun part1(lines: List<Long>): Long = sumOfGroveCoordinates(lines)

fun part2(lines: List<Long>): Long =
    sumOfGroveCoordinates(lines.map { it * 811589153L }, 10)

fun sumOfGroveCoordinates(lines: List<Long>, mixTimes: Int = 1): Long {
    val (list, linkedList) = lines.mapIndexed { index, i -> index to i }.let { it to it.toMutableList() }
    (1..mixTimes).forEach { _ ->
        list.forEach { number ->
            val index = linkedList.indexOf(number)
            linkedList.removeAt(index)
            linkedList.add((index + number.second).mod(linkedList.size), number)
        }
    }
    return linkedList.indexOfFirst { it.second == 0L }.let { indexOfZero ->
        listOf(1000, 2000, 3000).sumOf { linkedList[(indexOfZero + it) % lines.size].second }
    }
}

fun main() {
    val lines = File("src/main/kotlin/day20/day20_input.txt")
        .bufferedReader()
        .readLines()
        .map { it.toLong() }
    println(part1(lines))
    println(part2(lines))
}