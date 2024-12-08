package year2024.solutions.kotlin

import utils.data.Point
import utils.data.get
import utils.data.printGrid
import utils.setup.Day

fun main() = Day8().printDay()

class Day8 : Day(dayNumber = 8, year = 2024, useSampleInput = false) {
    private var input = inputList.map { str -> str.toCharArray() }

    private val hashMap = hashMapOf<Char, List<Point>>()

    init {
        input.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, char ->
                if (char != '.') {
                    hashMap[char] = hashMap.getOrDefault(char, listOf()) + Point(row, col)
                }
            }
        }
    }

    override fun partOne(): Any {
        input = inputList.map { str -> str.toCharArray() }
        var hashSet = hashSetOf<Point>()
        hashMap.forEach { (_, points) ->
            for (i in points.indices) {
                for (j in i + 1 until points.size) {
                    val rowDistance = points[i].row - points[j].row
                    val colDistance = points[i].col - points[j].col
                    val oneSide = Point(points[i].row + rowDistance, points[i].col + colDistance)
                    val otherSide = Point(points[j].row - rowDistance, points[j].col - colDistance)
                    if (input.get(oneSide) != null) {
                        if (input.get(oneSide) == '.') input[oneSide.row][oneSide.col] = '#'
                        hashSet.add(oneSide)
                    }
                    if (input.get(otherSide) != null) {
                        if (input.get(otherSide) == '.') input[otherSide.row][otherSide.col] = '#'
                        hashSet.add(otherSide)
                    }
                }
            }
        }
        input.printGrid()
        return hashSet.count()
    }

    override fun partTwo(): Any {
        input = inputList.map { str -> str.toCharArray() }
        var hashSet = hashSetOf<Point>()
        hashMap.forEach { (_, points) ->
            for (i in points.indices) {
                for (j in i + 1 until points.size) {
                    val rowDistance = points[i].row - points[j].row
                    val colDistance = points[i].col - points[j].col
                    var oneSide = Point(points[i].row + rowDistance, points[i].col + colDistance)
                    var otherSide = Point(points[j].row - rowDistance, points[j].col - colDistance)
                    while (input.get(oneSide) != null) {
                        if (input.get(oneSide) == '.') input[oneSide.row][oneSide.col] = '#'
                        hashSet.add(oneSide)
                        oneSide = Point(oneSide.row + rowDistance, oneSide.col + colDistance)
                    }
                    while (input.get(otherSide) != null) {
                        if (input.get(otherSide) == '.') input[otherSide.row][otherSide.col] = '#'
                        hashSet.add(otherSide)
                        otherSide = Point(otherSide.row - rowDistance, otherSide.col - colDistance)
                    }
                }
            }
        }
        input.printGrid()
        hashMap.values.forEach { points ->
            points.forEach { point ->
                hashSet.add(point)
            }
        }
        return hashSet.count()
    }
}