package year2024.solutions.kotlin

import utils.data.DirectionOrtho
import utils.data.Point
import utils.data.neighborsDelta
import utils.data.toDirectionOrtho
import utils.setup.Day

fun main() = Day12().printDay()

class Day12 : Day(dayNumber = 12, year = 2024, useSampleInput = false) {
    private val input = inputList.map { it.toCharArray() }
    val visited = Array(input.size) { BooleanArray(input[0].size) }
    val regions = mutableListOf<Triple<Int, Int, Set<Pair<Point, DirectionOrtho>>>>()

    override fun partOne(): Any {
        var answer = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (!visited[i][j]) {
                    val x = calculatePerimeter(i, j)
                    regions.add(x)
                    answer += x.first * x.second
                }
            }
        }
        return answer
    }

    override fun partTwo(): Any =
        regions.sumOf { (_, area, sides) ->
            val s = sides.groupBy { it.second }
                .map { it.key to it.value.map { it.first } }
                .sumOf {
                    when (it.first) {
                        DirectionOrtho.UP, DirectionOrtho.DOWN ->
                            it.second.groupBy { it.row }
                                .map { findAllInARow(it.value.map { it.col }).count() }


                        DirectionOrtho.RIGHT, DirectionOrtho.LEFT ->
                            it.second.groupBy { it.col }
                                .map { findAllInARow(it.value.map { it.row }).count() }
                    }.sum()
                }
            area * s
        }

    private fun calculatePerimeter(x: Int, y: Int): Triple<Int, Int, Set<Pair<Point, DirectionOrtho>>> {
        val queue = ArrayDeque<Pair<Int, Int>>()
        val type = input[x][y]
        queue.add(Pair(x, y))
        visited[x][y] = true
        var (perimeter, count) = 0 to 0
        val sides = mutableSetOf<Pair<Point, DirectionOrtho>>()
        while (queue.isNotEmpty()) {
            val (cx, cy) = queue.removeFirst()
            count++
            for ((dx, dy) in neighborsDelta) {
                val (nx, ny) = cx + dx to cy + dy
                val dir = Point(dx, dy).toDirectionOrtho()
                if (nx in input.indices && ny in input[0].indices) {
                    if (input[nx][ny] == type && !visited[nx][ny]) {
                        visited[nx][ny] = true
                        queue.add(Pair(nx, ny))
                    } else if (input[nx][ny] != type) {
                        perimeter++
                        sides.add(Point(cx, cy) to dir)
                    }
                } else {
                    perimeter++
                    sides.add(Point(cx, cy) to dir)
                }
            }
        }
        return Triple(perimeter, count, sides)
    }

    fun findAllInARow(numbers: List<Int>): List<List<Int>> {
        if (numbers.isEmpty()) return emptyList()
        val sortedNumbers = numbers.sorted()
        val result = mutableListOf<List<Int>>()
        var currentSequence = mutableListOf(sortedNumbers[0])
        for (i in 1 until sortedNumbers.size) {
            if (sortedNumbers[i] == sortedNumbers[i - 1] + 1) currentSequence.add(sortedNumbers[i])
            else {
                result.add(currentSequence)
                currentSequence = mutableListOf(sortedNumbers[i])
            }
        }
        result.add(currentSequence)
        return result
    }
}