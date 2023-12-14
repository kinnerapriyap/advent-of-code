package year2021.solutions

import utils.setup.Day

class Day2 : Day(dayNumber = 2, year = 2021, useSampleInput = true) {
    private val input = inputList.map { line ->
        val (instruction, value) = line.split(" ")
        when (instruction) {
            "forward" -> Instruction.Forward(value.toInt())
            "up" -> Instruction.Up(value.toInt())
            "down" -> Instruction.Down(value.toInt())
            else -> Instruction.Forward(0)
        }
    }

    override fun partOne(): Any {
        var horizontal = 0
        var depth = 0
        input.forEach { i ->
            when (i) {
                is Instruction.Down -> depth += i.value
                is Instruction.Forward -> horizontal += i.value
                is Instruction.Up -> depth -= i.value
            }
        }
        return horizontal * depth
    }

    override fun partTwo(): Any {
        var horizontal = 0
        var depth = 0
        var aim = 0
        input.forEach { i ->
            when (i) {
                is Instruction.Down -> aim += i.value
                is Instruction.Forward -> {
                    horizontal += i.value
                    depth += aim * i.value
                }
                is Instruction.Up -> aim -= i.value
            }
        }
        return horizontal * depth
    }

    sealed interface Instruction {
        val value: Int

        data class Forward(override val value: Int) : Instruction
        data class Up(override val value: Int) : Instruction
        data class Down(override val value: Int) : Instruction
    }
}