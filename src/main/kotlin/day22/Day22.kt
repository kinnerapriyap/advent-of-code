package day22

import day22.Face.Companion.getEdges
import java.io.File


data class Position(val row: Int, val col: Int, val direction: Direction)

fun part1(mapList: List<List<MapO>>, movements: List<Movement>): Int {
    val positions = mutableListOf(Position(0, mapList[0].indexOfFirst { it is MapO.Open }, Direction.RIGHT))
    movements.forEach { movement ->
        val new = when (movement) {
            is Movement.Turn -> movement.impl(positions.last(), mapList)
            is Movement.Walk -> movement.impl(positions.last(), mapList)
        }
        positions.addAll(new)
    }
    return positions.last().let { p -> 1000 * (p.row + 1) + 4 * (p.col + 1) + p.direction.getValue() }
}

fun part2(mapList: List<List<MapO>>, movements: List<Movement>): Int {
    val positions = mutableListOf(Position(0, mapList[0].indexOfFirst { it is MapO.Open }, Direction.RIGHT))
    movements.forEach { movement ->
        val new = when (movement) {
            is Movement.Turn -> movement.implCube(positions.last(), mapList)
            is Movement.Walk -> movement.implCube(positions.last(), mapList)
        }
        positions.addAll(new)
    }
    print(mapList, positions)
    print(positions.last())
    return positions.last().let { p -> 1000 * (p.row + 1) + 4 * (p.col + 1) + p.direction.getValue() }
}

fun Direction.getValue(): Int =
    when (this) {
        Direction.UP -> 3
        Direction.DOWN -> 1
        Direction.RIGHT -> 0
        Direction.LEFT -> 2
    }

fun main() {
    val lines = File("src/main/kotlin/day22/day22_input.txt")
        .bufferedReader()
        .readLines()
    val mapRaw = lines.take(lines.size - 2)
    val mapList = List(mapRaw.size) { row ->
        List(mapRaw.maxOf { it.length }) { col ->
            when (mapRaw.getOrNull(row)?.getOrNull(col)) {
                '.' -> MapO.Open
                '#' -> MapO.Wall
                else -> MapO.Off
            }
        }
    }
    val directions = lines.last().split(Regex("(?<=[RL])|(?=[RL])")).map {
        if (it.toIntOrNull() == null) Movement.Turn(it[0] == 'R')
        else Movement.Walk(it.toInt())
    }
    println(part1(mapList, directions))
    println(part2(mapList, directions))
}

fun print(mapList: List<List<MapO>>, positions: List<Position>) {
    val n = mapList.toMutableList().mapIndexed { rIndex, row ->
        row.toMutableList().mapIndexed { cIndex, c ->
            val p = positions.findLast { p -> p.row == rIndex && p.col == cIndex }
            if (p != null) {
                when (p.direction) {
                    Direction.UP -> '^'
                    Direction.DOWN -> 'v'
                    Direction.RIGHT -> '>'
                    Direction.LEFT -> '<'
                }
            } else c
        }
    }
    println(n.joinToString("\n") { it.joinToString("") })
}

sealed interface Movement {
    fun impl(position: Position, mapList: List<List<MapO>>): List<Position>
    fun implCube(position: Position, mapList: List<List<MapO>>): List<Position>

    data class Walk(val number: Int) : Movement {
        override fun impl(position: Position, mapList: List<List<MapO>>): List<Position> {
            var (row, col) = position.row to position.col
            val (dRow, dCol) = position.direction.row to position.direction.col
            var i = 0
            val positions = mutableListOf(position)
            while (i < number) {
                when (mapList.getOrNull(row + dRow)?.getOrNull(col + dCol)) {
                    MapO.Wall -> break
                    MapO.Open -> {
                        row += dRow
                        col += dCol
                    }

                    else -> {
                        val (r, c) = getNextAvailable(row, col, position.direction, mapList)
                        row = r
                        col = c
                    }
                }
                i++
                positions.add(Position(row, col, position.direction))
            }
            return positions
        }

        private fun getNextAvailable(
            row: Int,
            col: Int,
            direction: Direction,
            mapList: List<List<MapO>>
        ): Pair<Int, Int> {
            var (r, c) = row to col
            var (openR, openC) = row to col
            var next: MapO? = null
            while (next != MapO.Open) {
                when (direction) {
                    Direction.RIGHT, Direction.LEFT -> {
                        val range = if (direction == Direction.RIGHT) 0 until mapList[0].size - 1
                        else 1 until mapList[0].size
                        val newC =
                            if (c + direction.col in range) c + direction.col
                            else if (c + direction.col < 0) mapList[0].lastIndex
                            else 0
                        if (mapList[r][newC] is MapO.Wall) break
                        c = newC
                        if (mapList[r][newC] is MapO.Open) openC = newC
                    }

                    Direction.UP, Direction.DOWN -> {
                        val range = if (direction == Direction.DOWN) 0 until mapList.size - 1
                        else 1 until mapList.size
                        val newR =
                            if (r + direction.row in range) r + direction.row
                            else if (r + direction.row < 0) mapList.lastIndex
                            else 0
                        if (mapList[newR][c] is MapO.Wall) break
                        r = newR
                        if (mapList[newR][c] is MapO.Open) openR = newR
                    }
                }
                next = mapList[r][c]
            }
            return openR to openC
        }

        override fun implCube(position: Position, mapList: List<List<MapO>>): List<Position> {
            var i = 0
            val positions = mutableListOf(position)
            while (i < number) {
                val recent = positions.last()
                val (row, col, dir) = recent
                val (dRow, dCol) = recent.direction.row to recent.direction.col
                when (mapList.getOrNull(row + dRow)?.getOrNull(col + dCol)) {
                    MapO.Wall -> break
                    MapO.Open -> positions.add(Position(row + dRow, col + dCol, dir))
                    else -> {
                        val (pos, can) = getNextAvailableCube(recent, mapList)
                        if (can) positions.add(pos) else break
                    }
                }
                i++
            }
            return positions
        }

        private fun getNextAvailableCube(
            curr: Position,
            mapList: List<List<MapO>>
        ): Pair<Position, Boolean> {
            //val face = findTestFace(curr.row, curr.col)
            val face = findFace(curr.row, curr.col)
            val rowFace = curr.row - face.startRow
            val colFace = curr.col - face.startCol
            val edge = face.getEdges().let {
                when (curr.direction) {
                    Direction.UP -> it[0]
                    Direction.RIGHT -> it[1]
                    Direction.DOWN -> it[2]
                    Direction.LEFT -> it[3]
                }
            }
            val rows = edge.face.startRow to edge.face.startRow + cubeSize - 1
            val cols = edge.face.startCol to edge.face.startCol + cubeSize - 1
            val sameRow = edge.face.startRow + rowFace
            val sameCol = edge.face.startCol + colFace
            val (newR, newC) = when (curr.direction) {
                Direction.UP -> when (edge.direction) {
                    Direction.UP -> rows.second to sameCol
                    Direction.DOWN -> rows.first to cols.second - colFace
                    Direction.RIGHT -> edge.face.startRow + colFace to cols.first
                    Direction.LEFT -> rows.second - colFace to cols.second
                }

                Direction.DOWN -> when (edge.direction) {
                    Direction.UP -> rows.second to cols.second - colFace
                    Direction.DOWN -> rows.first to sameCol
                    Direction.RIGHT -> rows.second - colFace to cols.first
                    Direction.LEFT -> edge.face.startRow + colFace to cols.second
                }

                Direction.RIGHT -> when (edge.direction) {
                    Direction.UP -> rows.second to edge.face.startCol + rowFace
                    Direction.DOWN -> rows.first to cols.second - rowFace
                    Direction.RIGHT -> sameRow to cols.first
                    Direction.LEFT -> rows.second - rowFace to cols.second
                }

                Direction.LEFT -> when (edge.direction) {
                    Direction.UP -> rows.second to cols.second - rowFace
                    Direction.DOWN -> rows.first to edge.face.startCol + rowFace
                    Direction.RIGHT -> rows.second - rowFace to cols.first
                    Direction.LEFT -> sameRow to cols.second
                }
            }
            return if (mapList[newR][newC] is MapO.Wall) curr to false
            else Position(newR, newC, edge.direction) to true
        }
    }

    data class Turn(val isRight: Boolean) : Movement {
        override fun impl(position: Position, mapList: List<List<MapO>>): List<Position> {
            val newDirection = when (position.direction) {
                Direction.UP -> Direction.RIGHT to Direction.LEFT
                Direction.DOWN -> Direction.LEFT to Direction.RIGHT
                Direction.RIGHT -> Direction.DOWN to Direction.UP
                Direction.LEFT -> Direction.UP to Direction.DOWN
            }
            return listOf(
                Position(position.row, position.col, if (isRight) newDirection.first else newDirection.second)
            )
        }

        override fun implCube(position: Position, mapList: List<List<MapO>>): List<Position> = impl(position, mapList)
    }
}

enum class Direction(val row: Int, val col: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    RIGHT(0, +1),
    LEFT(0, -1)
}

sealed interface MapO {
    object Wall : MapO {
        override fun toString(): String = "#"
    }

    object Open : MapO {
        override fun toString(): String = "."
    }

    object Off : MapO {
        override fun toString(): String = "+"
    }
}

const val testSize = 4
const val inputSize = 50
const val cubeSize = inputSize