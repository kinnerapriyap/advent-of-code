package year2023.solutions

import utils.data.findLongestSequence
import utils.setup.Day

class Day14 : Day(dayNumber = 14, year = 2023, useSampleInput = false) {

    override fun partOne(): Any {
        val input = inputList.map { it.toCharArray() }
        input.tiltNorth()
        val size = input.size
        val ans = input.foldIndexed(0) { index, acc, chars ->
            acc + ((size - index) * chars.count { it == 'O' })
        }
        return ans
    }

    override fun partTwo(): Any {
        val input = inputList.map { it.toCharArray() }
        val size = input.size
        val points = mutableListOf<Int>()
        for (n in 0 until 200) {
            input.tiltNorth()
            input.tiltWest()
            input.tiltSouth()
            input.tiltEast()
            input.foldIndexed(0) { index, acc, chars ->
                acc + ((size - index) * chars.count { it == 'O' })
            }.let { points.add(it) }
        }
        val (start, length) = points.findLongestSequence()
        var repeats = start
        while (repeats + length < 1000000000) repeats += length
        return points.subList(start, start + length)[1000000000 - repeats - 1]
    }

    private fun List<CharArray>.tiltWest() {
        forEachIndexed { index, r ->
            var currR = -1
            for (i in r.indices) {
                when (r[i]) {
                    'O' -> {
                        this[index][i] = '.'
                        this[index][++currR] = 'O'
                    }

                    '#' -> currR = i
                    else -> {}
                }
            }
        }
    }

    private fun List<CharArray>.tiltEast() {
        forEachIndexed { index, r ->
            var currR = r.size
            for (i in r.indices.reversed()) {
                when (r[i]) {
                    'O' -> {
                        this[index][i] = '.'
                        this[index][--currR] = 'O'
                    }

                    '#' -> currR = i
                    else -> {}
                }
            }
        }
    }

    private fun List<CharArray>.tiltNorth() {
        this[0].indices.map { c -> this.indices.map { r -> this[r][c] } }
            .forEachIndexed { index, c ->
                var currR = -1
                for (i in c.indices) {
                    when (c[i]) {
                        'O' -> {
                            this[i][index] = '.'
                            this[++currR][index] = 'O'
                        }

                        '#' -> currR = i
                        else -> {}
                    }
                }
            }
    }

    private fun List<CharArray>.tiltSouth() {
        this[0].indices.map { c -> this.indices.map { r -> this[r][c] } }
            .forEachIndexed { index, c ->
                var currR = c.size
                for (i in c.indices.reversed()) {
                    when (c[i]) {
                        'O' -> {
                            this[i][index] = '.'
                            this[--currR][index] = 'O'
                        }

                        '#' -> currR = i
                        else -> {}
                    }
                }
            }
    }
}