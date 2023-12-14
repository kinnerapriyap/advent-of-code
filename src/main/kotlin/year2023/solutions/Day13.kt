package year2023.solutions

import utils.data.symmetricDifference
import utils.setup.Day

@OptIn(ExperimentalStdlibApi::class)
class Day13 : Day(dayNumber = 13, year = 2023, useSampleInput = false) {
    override fun partOne(): Any {
        return inputString.split("\n\n")
            .map { it.split("\n") }
            .sumOf {
                for (i in 0..<it.lastIndex) {
                    val one = it.subList(0, i + 1).reversed()
                    val two = it.subList(i + 1, it.size)
                    val size = minOf(one.size, two.size)
                    if (one.take(size) == two.take(size)) return@sumOf (i + 1) * 100
                }
                val col = it[0].indices.map { c -> it.indices.map { r -> it[r][c] } }
                for (i in 0..<it[0].lastIndex) {
                    val one = col.subList(0, i + 1).reversed()
                    val two = col.subList(i + 1, col.size)
                    val size = minOf(one.size, two.size)
                    if (one.take(size) == two.take(size)) return@sumOf i + 1
                }
                return@sumOf 0
            }
    }

    override fun partTwo(): Any {
        return inputString.split("\n\n")
            .map { it.split("\n") }
            .sumOf {
                for (i in 0..<it.lastIndex) {
                    val one = it.slice(0..i).reversed()
                    val two = it.slice(i + 1..it.lastIndex)
                    val size = minOf(one.size, two.size)
                    (one.take(size) symmetricDifference two.take(size)).toList().let { t ->
                        if (t.size == 2) {
                            val uncommon = t[0].foldIndexed(0) { index, acc, c ->
                                if (t[1][index] == c) acc else acc + 1
                            }
                            if (uncommon == 1) return@sumOf (i + 1) * 100
                        }
                    }

                }
                val col = it[0].indices.map { c -> it.indices.map { r -> it[r][c] } }
                for (i in it[0].lastIndex - 1 downTo 0) {
                    val one = col.slice(0..i).reversed()
                    val two = col.slice(i + 1..col.lastIndex)
                    val size = minOf(one.size, two.size)
                    (one.take(size) symmetricDifference two.take(size)).toList().let { t ->
                        if (t.size == 2) {
                            val uncommon = t[0].foldIndexed(0) { index, acc, c ->
                                if (t[1][index] == c) acc else acc + 1
                            }
                            if (uncommon == 1) return@sumOf (i + 1)
                        }
                    }
                }
                return@sumOf 0
            }
    }
}