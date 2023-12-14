package year2021.solutions

import utils.data.symmetricDifference
import utils.setup.Day

class Day8 : Day(dayNumber = 8, year = 2021, useSampleInput = false) {
    private val input = inputList.map { str ->
        val (examples, output) = str.split("|").map { s ->
            s.split(" ").map { it.trim() }.filter { it.isNotEmpty() }
        }
        examples to output
    }

    override fun partOne(): Any {
        return input.map { it.second }.sumOf { o -> o.count { it.length in listOf(3, 4, 2, 7) } }
    }

    override fun partTwo(): Any {
        return input.mapNotNull { (e, o) ->
            val ans = Array(10) { setOf<Char>() }
            val rem = e.toMutableList()
            rem.toList().forEach {
                when (it.length) {
                    2 -> ans[1] = it.toSet()
                    3 -> ans[7] = it.toSet()
                    4 -> ans[4] = it.toSet()
                    7 -> ans[8] = it.toSet()
                }
                if (it.length in listOf(2, 3, 4, 7)) rem.remove(it)
            }
            rem.toList().forEach {
                if (it.length == 5) {
                    if (ans[1].isNotEmpty() && it.toSet().containsAll(ans[1])) {
                        ans[3] = it.toSet()
                        rem.remove(it)
                    }
                    if (ans[7].isNotEmpty() && it.toSet().containsAll(ans[7])) {
                        ans[3] = it.toSet()
                        rem.remove(it)
                    }
                }
                if (it.length == 6) {
                    if (ans[4].isNotEmpty() && it.toSet().containsAll(ans[4])) {
                        ans[9] = it.toSet()
                        rem.remove(it)
                    }
                    if (ans[7].isNotEmpty() && it.toSet().containsAll(ans[7]) && !it.toSet().containsAll(ans[4])) {
                        ans[0] = it.toSet()
                        rem.remove(it)
                    }
                }
                rem.filter { r -> r.length == 6 }.let { list ->
                    if (list.size == 1) {
                        ans[6] = list[0].toSet()
                        rem.remove(list[0])
                    }
                }
                if (ans[6].isNotEmpty() && rem.size == 2) {
                    val (x, y) = rem[0] to rem[1]
                    if ((x.toSet() symmetricDifference ans[6]).size == 1) {
                        ans[5] = x.toSet()
                        ans[2] = y.toSet()
                    } else {
                        ans[5] = y.toSet()
                        ans[2] = x.toSet()
                    }
                    rem.remove(x)
                    rem.remove(y)
                }
            }
            o.map { ans.indexOf(it.toSet()) }.joinToString("").toIntOrNull()
        }.sum()
    }
}