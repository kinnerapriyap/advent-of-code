package year2023.solutions

import utils.setup.Day

class Day15 : Day(dayNumber = 15, year = 2023, useSampleInput = true) {
    private val input = inputString.split(",")

    override fun partOne(): Any {
        return input.sumOf { s -> s.getHash() }
    }

    override fun partTwo(): Any {
        val output = hashMapOf<Int, MutableList<Pair<String, Int?>>>()
        input.forEach { s ->
            if (s.contains("=")) {
                val (str, num) = s.split('=')
                val hash = str.getHash()
                val exists = output[hash]?.indexOfFirst { it.first == str } ?: -1
                if (exists != -1) {
                    output[hash] = (output[hash] ?: mutableListOf()).apply {
                        removeAt(exists)
                        add(exists, str to num.toInt())
                    }
                } else {
                    output[hash] = (output[hash] ?: mutableListOf()).apply {
                        add(str to num.toInt())
                    }
                }
            } else {
                val (str, _) = s.split('-')
                val hash = str.getHash()
                output[hash] = (output[hash] ?: mutableListOf()).apply {
                    val exists = output[hash]?.indexOfFirst { it.first == str } ?: -1
                    if (exists != -1) removeAt(exists)
                }
            }
        }
        return output.map { (box, lenses) ->
            lenses.mapIndexed { index, pair -> (box + 1) * (index + 1) * (pair.second ?: 0) }.sum()
        }.sum()
    }

    private fun String.getHash(): Int = this.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }
}