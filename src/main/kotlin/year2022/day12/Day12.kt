package year2022.day12

import utils.data.Point
import java.io.File

fun part1(start: Point, end: Point, lines: List<CharArray>): Int = shortestPathDistance(start, end, lines)

fun part2(end: Point, lines: List<CharArray>): Int =
    lines.foldIndexed(Int.MAX_VALUE) { row, rowAcc, line ->
        minOf(
            rowAcc,
            line.foldIndexed(Int.MAX_VALUE) { col, colAcc, c ->
                if (c == 'a') minOf(colAcc, shortestPathDistance(Point(row, col), end, lines))
                else colAcc
            }
        )
    }

fun main() {
    val lines = File("src/main/kotlin/year2022.day12/day12_input.txt")
        .bufferedReader()
        .readLines()
        .map { line -> line.chunked(1).map { it[0] }.toCharArray() }
    val startRow = lines.indexOfFirst { it.any { c -> c == 'S' } }
    val startCol = lines[startRow].indexOfFirst { c -> c == 'S' }
    val endRow = lines.indexOfFirst { it.any { c -> c == 'E' } }
    val endCol = lines[endRow].indexOfFirst { c -> c == 'E' }
    lines[startRow][startCol] = 'a'
    lines[endRow][endCol] = 'z'
    println(part1(Point(startRow, startCol), Point(endRow, endCol), lines))
    println(part2(Point(endRow, endCol), lines))
}

fun getNeighbours(Point: Point) = listOf(
    Point((Point.row + 1), Point.col),
    Point((Point.row - 1), Point.col),
    Point(Point.row, (Point.col + 1)),
    Point(Point.row, (Point.col - 1)),
)

fun shortestPathDistance(start: Point, end: Point, lines: List<CharArray>): Int {
    val distances = lines.map { it.map { Int.MAX_VALUE }.toMutableList() }
    distances[start.row][start.col] = 0
    val queue = mutableListOf(start)
    while (queue.isNotEmpty()) {
        val v = queue.removeFirst()
        getNeighbours(v)
            .filter { (nRow, nCol) ->
                lines.indices.contains(nRow) &&
                        lines[nRow].indices.contains(nCol) &&
                        lines[v.row][v.col] + 1 >= lines[nRow][nCol] &&
                        distances[v.row][v.col] + 1 < distances[nRow][nCol]
            }
            .forEach {
                distances[it.row][it.col] = distances[v.row][v.col] + 1
                queue.add(it)
            }
    }
    return distances[end.row][end.col]
}
