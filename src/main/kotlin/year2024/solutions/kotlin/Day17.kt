package year2024.solutions.kotlin

import utils.setup.Day
import kotlin.math.pow

fun main() = Day17().printDay()

class Day17 : Day(dayNumber = 17, year = 2024, useSampleInput = false) {
    private val registers = inputList.subList(0, inputList.indexOf("")).map { it.split(": ")[1].toLong() }
    private val program = inputList.subList(inputList.indexOf("") + 1, inputList.size)[0]
        .split(": ")[1].split(",").map { it.toLong() }
    var aStart: Long = registers[0]
    override fun partOne(): Any {
        var a = aStart
        var b = registers[1]
        var c = registers[2]
        var i = 0
        var output = mutableListOf<Long>()
        while (i < program.size) {
            val (opCode, operand) = program[i] to program[i + 1]
            val combo = operand.toOperand(a, b, c)
            when (opCode) {
                0L -> a = a / 2.0.pow(combo.toDouble()).toLong()
                1L -> b = b xor operand
                2L -> b = combo % 8
                3L -> if (a != 0L) {
                    i = operand.toInt()
                    continue
                }

                4L -> b = b xor c
                5L -> output.add(combo % 8)
                6L -> b = a / 2.0.pow(combo.toDouble()).toLong()
                7L -> c = a / 2.0.pow(combo.toDouble()).toLong()
            }
            i += 2
        }
        return output
    }

    override fun partTwo(): Any {
        /*b = a % 8
        b = b ^ 1
        c = a / 2^b
        b = b ^ c
        a = a / 8
        b = b ^ 6
        print b % 8*/
        aStart = registers[0]
        return findMatching(program)
    }

    private fun findMatching(target: List<Long>): Long {
        aStart = if (target.size == 1) 0 else 8 * findMatching(target.subList(1, target.size))
        while (partOne() != target) aStart++
        return aStart
    }

    private fun Long.toOperand(a: Long, b: Long, c: Long): Long = when (this) {
        0L, 1L, 2L, 3L -> this
        4L -> a
        5L -> b
        6L -> c
        else -> this
    }
}