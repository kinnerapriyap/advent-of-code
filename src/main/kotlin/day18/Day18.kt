package day18

import java.io.File

fun part1(lines: List<Point3D>): Int =
    lines.sumOf { point -> 6 - lines.intersect(point.getFaces()).size }

fun part2(lines: List<Point3D>): Int {
    val start = Point3D(lines.minOf { it.x } - 1, lines.minOf { it.y } - 1, lines.minOf { it.z } - 1)
    val max = Point3D(lines.maxOf { it.x } + 1, lines.maxOf { it.y } + 1, lines.maxOf { it.z } + 1)
    val steam = mutableSetOf<Point3D>().apply {
        add(start)
        val toVisit = mutableListOf(start)
        while (toVisit.isNotEmpty()) {
            toVisit.removeFirst().getFaces()
                .filter { face -> face !in lines }
                .filter { face ->
                    face.x in start.x..max.x &&
                            face.y in start.y..max.y &&
                            face.z in start.z..max.z
                }
                .forEach { face -> add(face) && toVisit.add(face) }
        }
    }
    return lines.sumOf { point -> steam.intersect(point.getFaces()).size }
}

fun main() {
    val lines = File("src/main/kotlin/day18/day18_input.txt")
        .bufferedReader()
        .readLines()
        .map { it.split(",").map { no -> no.toInt() } }
        .map { (x, y, z) -> Point3D(x, y, z) }
    println(part1(lines))
    println(part2(lines))
}

data class Point3D(val x: Int, val y: Int, val z: Int) {
    override fun toString(): String = "($x,$y,$z)"
    fun getFaces() = setOf(
        Point3D(x + 1, y, z), Point3D(x - 1, y, z),
        Point3D(x, y + 1, z), Point3D(x, y - 1, z),
        Point3D(x, y, z + 1), Point3D(x, y, z - 1)
    )
}