package year2022.day09

import java.io.File

fun part1(lines: List<Pair<Char, Int>>): Int = getNumberOfPositions(lines, tailSize = 1)

fun part2(lines: List<Pair<Char, Int>>): Int = getNumberOfPositions(lines, tailSize = 9)

fun getNumberOfPositions(lines: List<Pair<Char, Int>>, tailSize: Int): Int {
    var positions = List(2 + 2 * tailSize) { 0 }
    return lines.fold(hashSetOf(0 to 0)) { set, (direction, steps) ->
        val sol = implLine(direction, steps, positions, tailSize)
        set.addAll(sol.first)
        positions = sol.second
        set
    }.size
}

fun main() {
    val lines = File("src/main/kotlin/year2022.day09/day9_input.txt")
        .bufferedReader()
        .readLines()
        .map { line ->
            val (direction, steps) = line.split(" ", limit = 2)
            direction.toCharArray()[0] to steps.toInt()
        }
    println(part1(lines))
    println(part2(lines))
}

fun implLine(
    direction: Char,
    steps: Int,
    positions: List<Int>,
    tailSize: Int
): Pair<HashSet<Pair<Int, Int>>, List<Int>> {
    val visited = hashSetOf(0 to 0)
    val pos = positions.toMutableList()
    for (i in 1..steps) {
        when (direction) {
            'L' -> pos[0]--
            'R' -> pos[0]++
            'U' -> pos[1]++
            'D' -> pos[1]--
        }
        for (j in 1..tailSize) {
            val (tailX, tailY) = follow(pos.subList(2 * j - 2, 2 * j + 2))
            pos[2 * j] = tailX
            pos[2 * j + 1] = tailY
            if (j == tailSize) visited.add(tailX to tailY)
        }
    }
    return visited to pos
}

fun follow(positions: List<Int>): Pair<Int, Int> {
    val (headX, headY) = positions.subList(0, 2)
    var (tailX, tailY) = positions.subList(2, 4)
    when {
        headX + 2 == tailX -> {
            tailX--
            when (tailY) {
                headY - 2 -> tailY++
                headY + 2 -> tailY--
                else -> tailY = headY
            }
        }

        headX - 2 == tailX -> {
            tailX++
            when (tailY) {
                headY - 2 -> tailY++
                headY + 2 -> tailY--
                else -> tailY = headY
            }
        }

        headY - 2 == tailY -> {
            tailY++
            tailX = headX
        }

        headY + 2 == tailY -> {
            tailY--
            tailX = headX
        }
    }
    return tailX to tailY
}