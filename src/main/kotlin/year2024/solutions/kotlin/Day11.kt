package year2024.solutions.kotlin

import utils.setup.Day

fun main() = Day11().printDay()

class Day11 : Day(dayNumber = 11, year = 2024, useSampleInput = false) {
    private val input = inputString.split(" ").map { it.toLong() }
    override fun partOne(): Any {
        var current = input
        repeat(25) {
            var new = mutableListOf<Long>()
            current.forEach {
                when {
                    it == 0L -> new.add(1)
                    it.toString().length % 2 == 0 -> {
                        new.add(it.toString().substring(0, it.toString().length / 2).toLong())
                        new.add(it.toString().substring(it.toString().length / 2).toLong())
                    }

                    else -> new.add(it * 2024)
                }
            }
            current = new
        }
        return current.size
    }

    override fun partTwo(): Any {
        val cache = mutableMapOf<Long, List<Long>>()
        var c = 0
        for (i in input) {
            var current = listOf(i)
            repeat(75) {
                var new = mutableListOf<Long>()
                for (num in current) {
                    new.addAll(
                        cache.getOrPut(num) {
                            when {
                                num == 0L -> listOf(1L)
                                num.toString().length % 2 == 0 -> {
                                    val str = num.toString()
                                    listOf(
                                        str.substring(0, str.length / 2).toLong(),
                                        str.substring(str.length / 2).toLong()
                                    )
                                }

                                else -> listOf(num * 2024)
                            }
                        }
                    )
                }
                current = new
            }
            c == current.size
        }
        return c
    }
}