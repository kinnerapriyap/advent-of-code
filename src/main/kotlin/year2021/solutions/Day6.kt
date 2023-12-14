package year2021.solutions

import utils.setup.Day

class Day6 : Day(dayNumber = 6, year = 2021, useSampleInput = false) {
    private val input = inputString.split(",").mapNotNull { it.trim().toIntOrNull() }

    override fun partOne(): Any {
        val ans = input.toMutableList()
        for (i in 1..80) {
            for (index in 0..ans.lastIndex) {
                ans[index] = if (ans[index] == 0) {
                    ans.add(8)
                    6
                } else ans[index] - 1
            }
        }
        return ans.size
    }

    override fun partTwo(): Any {
        var ans = hashMapOf<Int, Long>()
        input.forEach { ans[it] = ans.getOrDefault(it, 0L) + 1L }
        for (x in 1..256) {
            val new = hashMapOf<Int, Long>()
            ans.forEach { (num, u) ->
                if (num == 0) {
                    new[8] = new.getOrDefault(8, 0L) + u
                    new[6] = new.getOrDefault(6, 0L) + u
                } else new[num - 1] = new.getOrDefault(num - 1, 0L) + u
            }
            ans = new
        }
        return ans.values.sum()
    }
}