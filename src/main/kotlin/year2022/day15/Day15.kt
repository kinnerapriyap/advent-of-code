package year2022.day15

import java.io.File
import kotlin.math.abs

fun part1(reports: List<Report>): Int {
    val sensors = reports.map { it.sensorRow to it.sensorCol }
    val beacons = reports.map { it.beaconRow to it.beaconCol }
    return reports.fold(setOf<Pair<Int, Int>>()) { acc, report ->
        val (sensorCol, sensorRow, beaconCol, beaconRow) = report
        val max = abs(sensorCol - beaconCol) + abs(sensorRow - beaconRow)
        val count = hashSetOf<Pair<Int, Int>>()
        for (i in -max..max) {
            val row = sensorRow + i
            if (row == 2000000) {
                for (j in sensorCol - max + abs(i)..sensorCol + max - abs(i)) {
                    if (!sensors.contains(row to j) && !beacons.contains(row to j)) count.add(row to j)
                }
            }
        }
        acc + count
    }.size
}

fun part2(reports: List<Report>): Long {
    reports.forEach { report ->
        for (row in report.sensorRow - report.radius - 1..report.sensorRow + report.radius + 1) {
            val leftX = report.sensorCol - report.radius - 1 + abs(report.sensorRow - row)
            val rightX = report.sensorCol + report.radius + 1 - abs(report.sensorRow - row)
            if (leftX < 0 || leftX > ALLOWED || rightX < 0 || rightX > ALLOWED || row < 0 || row > ALLOWED) continue
            if (reports.none { it.radius >= it.distance(leftX, row) }) return (leftX.toLong() * ALLOWED) + row
            if (reports.none { it.radius >= it.distance(rightX, row) }) return (rightX.toLong() * ALLOWED) + row
        }
    }
    return 0L
}

fun main() {
    val lines = File("src/main/kotlin/year2022.day15/day15_input.txt")
        .bufferedReader()
        .readLines()
        .map { line ->
            val sensorCol = line.substringAfter("x=").split(",")[0]
            val (sensorRow, beacon) = line.substringAfter("y=").split(":")
            val beaconCol = beacon.substringAfter("x=").split(",")[0]
            val beaconRow = beacon.substringAfter("y=")
            Report(sensorCol.toInt(), sensorRow.toInt(), beaconCol.toInt(), beaconRow.toInt())
        }
    println(part1(lines))
    println(part2(lines))
}

const val ALLOWED = 4000000

data class Report(val sensorCol: Int, val sensorRow: Int, val beaconCol: Int, val beaconRow: Int) {
    val radius = abs(sensorCol - beaconCol) + abs(sensorRow - beaconRow)
    fun distance(x: Int, y: Int) = abs(sensorCol - x) + abs(sensorRow - y)
}