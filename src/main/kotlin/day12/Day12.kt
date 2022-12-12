package day12

import java.io.File

data class Vertex(val row: Int, val column: Int)

fun part1(start: Vertex, end: Vertex, lines: List<CharArray>): Int = shortestPathDistance(start, end, lines)

fun part2(end: Vertex, lines: List<CharArray>): Int =
    lines.foldIndexed(Int.MAX_VALUE) { row, rowAcc, line ->
        minOf(
            rowAcc,
            line.foldIndexed(Int.MAX_VALUE) { col, colAcc, c ->
                if (c == 'a') minOf(colAcc, shortestPathDistance(Vertex(row, col), end, lines))
                else colAcc
            }
        )
    }

fun main() {
    val lines = File("src/main/kotlin/day12/day12_input.txt")
        .bufferedReader()
        .readLines()
        .map { line -> line.chunked(1).map { it[0] }.toCharArray() }
    val startRow = lines.indexOfFirst { it.any { c -> c == 'S' } }
    val startCol = lines[startRow].indexOfFirst { c -> c == 'S' }
    val endRow = lines.indexOfFirst { it.any { c -> c == 'E' } }
    val endCol = lines[endRow].indexOfFirst { c -> c == 'E' }
    lines[startRow][startCol] = 'a'
    lines[endRow][endCol] = 'z'
    println(part1(Vertex(startRow, startCol), Vertex(endRow, endCol), lines))
    println(part2(Vertex(endRow, endCol), lines))
}

fun getNeighbours(vertex: Vertex) = listOf(
    Vertex((vertex.row + 1), vertex.column),
    Vertex((vertex.row - 1), vertex.column),
    Vertex(vertex.row, (vertex.column + 1)),
    Vertex(vertex.row, (vertex.column - 1)),
)

fun shortestPathDistance(start: Vertex, end: Vertex, lines: List<CharArray>): Int {
    val distances = lines.map { it.map { Int.MAX_VALUE }.toMutableList() }
    distances[start.row][start.column] = 0
    val queue = mutableListOf(start)
    while (queue.isNotEmpty()) {
        val v = queue.removeFirst()
        getNeighbours(v)
            .filter { (nRow, nCol) ->
                lines.indices.contains(nRow) &&
                        lines[nRow].indices.contains(nCol) &&
                        lines[v.row][v.column] + 1 >= lines[nRow][nCol] &&
                        distances[v.row][v.column] + 1 < distances[nRow][nCol]
            }
            .forEach {
                distances[it.row][it.column] = distances[v.row][v.column] + 1
                queue.add(it)
            }
    }
    return distances[end.row][end.column]
}
