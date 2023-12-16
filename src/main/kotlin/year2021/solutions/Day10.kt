package year2021.solutions

import utils.setup.Day

class Day10 : Day(dayNumber = 10, year = 2021, useSampleInput = false) {

    override fun partOne(): Any {
        return inputList.mapNotNull A@{
            val q = mutableListOf<Char>()
            it.forEach { c ->
                if (c in opening) q.add(c)
                else if (closing.indexOf(c) != opening.indexOf(q.removeLast())) return@A c
            }
            return@A null
        }.map {
            when (it) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0
            }
        }.sum()
    }

    override fun partTwo(): Any {
        return inputList.filter {
            var ans = true
            val q = mutableListOf<Char>()
            it.forEach { c ->
                if (c in opening) q.add(c)
                else if (closing.indexOf(c) != opening.indexOf(q.removeLast())) ans = false
            }
            ans
        }.map {
            val q = mutableListOf<Char>()
            it.forEach { c ->
                if (c in opening) q.add(c)
                else opening.indexOf(q.removeLast())
            }
            q.map { a -> closing[opening.indexOf(a)] }.reversed()
        }.map {
            var ans = 0L
            it.forEach { c ->
                ans *= 5
                ans += when (c) {
                    ')' -> 1
                    ']' -> 2
                    '}' -> 3
                    '>' -> 4
                    else -> 0
                }
            }
            ans
        }.sorted().let {
            val middle = it.size / 2
            it[middle]
        }
    }

    private val opening = listOf('[', '{', '(', '<')
    private val closing = listOf(']', '}', ')', '>')
}