package year2023.solutions

import utils.data.Point3D
import utils.setup.Day
import kotlin.math.abs

class Day22 : Day(dayNumber = 22, year = 2023, useSampleInput = false) {
    data class Block(val i: Int, val start: Point3D, val end: Point3D, val orientation: Orientation, val length: Int)
    enum class Orientation { X, Y, Z }

    private val input = inputList.mapIndexed { index, str ->
        str.split("~").map { p ->
            p.split(",")
                .map { it.toInt() }
                .let { Point3D(it[0], it[1], it[2]) }
        }.let { (one, two) ->
            val o = if (one.x != two.x) Orientation.X else if (one.y != two.y) Orientation.Y else Orientation.Z
            val length = when (o) {
                Orientation.X -> one.x - two.x
                Orientation.Y -> one.y - two.y
                Orientation.Z -> one.z - two.z
            }
            Block(index + 1, one, two, o, abs(length) + 1)
        }
    }
    private val xSize = 10
    private val ySize = 10
    private val zSize = 365
    private val grid = Array(zSize) { Array(xSize) { Array(ySize) { 0 } } } // z, x, y

    private val inputAfterFalling: List<Triple<Int, List<Point3D>, Orientation>>
    private val hashMap = hashMapOf<Int, MutableSet<Int>>()

    init {
        inputAfterFalling = input.blockFall()
        inputAfterFalling.forEach { (brick, points, o) ->
            when (o) {
                Orientation.X -> points.map { Point3D(it.x, points[0].y, points[0].z + 1) }
                Orientation.Y -> points.map { Point3D(points[0].x, it.y, points[0].z + 1) }
                Orientation.Z -> listOf(Point3D(points[0].x, points[0].y, points.maxOf { it.z } + 1))
            }.forEach {
                hashMap[brick] = hashMap.getOrPut(brick) { mutableSetOf() }
                    .apply { grid[it.z][it.x][it.y].let { v -> if (v != 0) add(v) } }
            }
        }
    }

    override fun partOne(): Any {
        return hashMap.count { (brick, supports) ->
            supports.all { hashMap.any { (b, s) -> b != brick && it in s } }
        }
    }

    override fun partTwo(): Any {
        return hashMap.keys.sumOf { brick ->
            input.toMutableList()
                .apply { removeIf { it.i == brick } }
                .blockFall()
                .count { (br, points, _) ->
                    inputAfterFalling.find { b -> b.first == br }!!.second != points
                }
        }
    }

    private fun List<Block>.blockFall(): List<Triple<Int, List<Point3D>, Orientation>> {
        val minZ = Array(xSize) { IntArray(ySize) { 1 } }
        return sortedBy { minOf(it.start.z, it.end.z) }
            .map { (i, start, end, o, l) ->
                val points = when (o) {
                    Orientation.X -> {
                        val newStartZ = minZ.slice(start.x..end.x).maxOf { it[start.y] }
                        (start.x..end.x).map { Point3D(it, start.y, newStartZ) }
                            .also { (start.x..end.x).forEach { xI -> minZ[xI][start.y] = newStartZ + 1 } }
                    }

                    Orientation.Y -> {
                        val newStartZ = minZ[start.x].slice(start.y..end.y).max()
                        (start.y..end.y).map { Point3D(start.x, it, newStartZ) }
                            .also { (start.y..end.y).forEach { yI -> minZ[start.x][yI] = newStartZ + 1 } }
                    }

                    Orientation.Z -> {
                        val newStartZ = minZ[start.x][start.y]
                        (newStartZ until newStartZ + l).map { Point3D(start.x, start.y, it) }
                            .also { minZ[start.x][start.y] = newStartZ + l }
                    }
                }
                points.forEach { point -> grid[point.z][point.x][point.y] = i }
                Triple(i, points, o)
            }
    }
}