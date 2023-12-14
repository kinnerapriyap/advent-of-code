package year2023.solutions

import utils.data.lcm
import utils.setup.Day

class Day8 : Day(dayNumber = 8, year = 2023, useSampleInput = false) {
    private val input = inputList.subList(2, inputList.size).associate { str ->
        val (root, rest) = str.split("=")
        val (left, right) = rest.split(",").map { it.trim() }
        root.trim() to Pair(left.substring(1), right.substring(0, right.lastIndex))
    }
    private val directions = inputList[0].repeat(100)

    override fun partOne(): Any {
        var (s, pair) = "AAA" to input.getOrElse("AAA") { return 0 }
        var count = 0
        directions.forEach {
            s = if (it == 'R') pair.second else pair.first
            pair = input[s]!!
            count++
            if (s == "ZZZ") return count
        }
        return 0
    }

    override fun partTwo(): Any {
        var ans = Long.MIN_VALUE
        input.filterKeys { it.last() == 'A' }.forEach {
            var (s, pair) = it.key to it.value
            var (dI, count) = 0 to 0L
            while (s.last() != 'Z') {
                s = if (directions[dI] == 'R') pair.second else pair.first
                pair = input[s]!!
                count++
                dI++
            }
            ans = if (ans == Long.MIN_VALUE) count else ans.lcm(count)
        }
        return ans
    }
}