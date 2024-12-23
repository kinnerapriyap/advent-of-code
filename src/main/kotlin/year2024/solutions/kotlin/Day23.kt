package year2024.solutions.kotlin

import utils.setup.Day

fun main() = Day23().printDay()

class Day23 : Day(dayNumber = 23, year = 2024, useSampleInput = true) {
    // qp-kh
    private val input = inputList.map { str -> str.split("-") }

    override fun partOne(): Any {
        val connections = mutableSetOf<Pair<String, String>>()
        input.forEach { edge ->
            val (a, b) = edge
            connections.add(Pair(a, b))
            connections.add(Pair(b, a))
        }
        val triangles = mutableSetOf<Set<String>>()
        connections.forEach { (a, b) ->
            connections.filter { it.first == b }.forEach { (b, c) ->
                if (connections.contains(Pair(c, a))) {
                    triangles.add(setOf(a, b, c))
                }
            }
        }
        return triangles.count { it.any { x -> x.startsWith('t') } }
    }
}