package year2021.solutions

import utils.data.Point
import utils.data.get
import utils.setup.Day

class Day11 : Day(dayNumber = 11, year = 2021, useSampleInput = false) {
    private val input = inputList.map { str -> str.mapNotNull { it.digitToInt() }.toMutableList() }

    override fun partOne(): Any {
        var ans = 0
        repeat(100) {
            input.forEachIndexed { row, chars ->
                chars.forEachIndexed { col, i ->
                    input[row][col] += 1
                }
            }
            val q = input.mapIndexed { row, chars ->
                chars.mapIndexedNotNull { col, i ->
                    if (i > 9) Point(row, col) else null
                }
            }.flatten().toMutableList()
            val flashed = mutableListOf<Point>()
            while (q.isNotEmpty()) {
                val p = q.removeFirst()
                if (p in flashed) continue
                flashed.add(p)
                p.allSides()
                    .filter { input.get(it) != null }
                    .forEach {
                        input[it.row][it.col] += 1
                        if (input.get(it)!! > 9) q.add(it)
                    }
            }
            flashed.forEach { input[it.row][it.col] = 0 }
            ans += flashed.size
        }
        return ans
    }

    override fun partTwo(): Any {
        var step = 0
        while (!input.all { r -> r.all { it == 0 } }) {
            input.forEachIndexed { row, chars ->
                chars.forEachIndexed { col, i ->
                    input[row][col] += 1
                }
            }
            val q = input.mapIndexed { row, chars ->
                chars.mapIndexedNotNull { col, i ->
                    if (i > 9) Point(row, col) else null
                }
            }.flatten().toMutableList()
            val flashed = mutableListOf<Point>()
            while (q.isNotEmpty()) {
                val p = q.removeFirst()
                if (p in flashed) continue
                flashed.add(p)
                p.allSides()
                    .filter { input.get(it) != null }
                    .forEach {
                        input[it.row][it.col] += 1
                        if (input.get(it)!! > 9) q.add(it)
                    }
            }
            flashed.forEach { input[it.row][it.col] = 0 }
            step += 1
        }
        return step
    }
}