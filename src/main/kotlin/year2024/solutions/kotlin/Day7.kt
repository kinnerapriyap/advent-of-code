package year2024.solutions.kotlin

import utils.setup.Day

fun main() = Day7().printDay()

class Day7 : Day(dayNumber = 7, year = 2024, useSampleInput = false) {
    private val input = inputList.map { str ->
        val (test, nums) = str.split(": ")
        test.toLong() to nums.split(" ").map { it.toLong() }
    }

    override fun partOne() = input.filter { pair -> can(pair) }.sumOf { pair -> pair.first }

    override fun partTwo() = input.filter { pair -> can(pair, true) }.sumOf { pair -> pair.first }

    private fun can(t: Pair<Long, List<Long>>, two: Boolean = false): Boolean {
        val (target, nums) = t
        fun evaluate(expression: List<Any>): Long {
            var result = expression[0] as Long
            var i = 1
            while (i < expression.size) {
                val num = expression[i + 1] as Long
                result = when (expression[i] as Operation) {
                    Operation.ADD -> result + num
                    Operation.MUL -> result * num
                    Operation.PAR -> if (two) (result.toString() + num.toString()).toLong() else result
                }
                i += 2
            }
            return result
        }

        fun generateExpressions(index: Int, current: List<Any>): Boolean {
            if (index == nums.size) return evaluate(current) == target
            val nextNum = nums[index]
            return generateExpressions(index + 1, current + listOf(Operation.ADD, nextNum)) ||
                    generateExpressions(index + 1, current + listOf(Operation.MUL, nextNum)) ||
                    (two && generateExpressions(index + 1, current + listOf(Operation.PAR, nextNum)))
        }
        return generateExpressions(1, listOf(nums[0]))
    }

    enum class Operation { ADD, MUL, PAR }
}