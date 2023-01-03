package year2021.day02

import java.io.File

fun part1(lines: List<Instruction>): Int {
    var horizontal = 0
    var depth = 0
    lines.forEach { i ->
        when (i) {
            is Instruction.Down -> depth += i.value
            is Instruction.Forward -> horizontal += i.value
            is Instruction.Up -> depth -= i.value
        }
    }
    return horizontal * depth
}

fun part2(lines: List<Instruction>): Int {
    var horizontal = 0
    var depth = 0
    var aim = 0
    lines.forEach { i ->
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

fun main() {
    val lines = File("src/main/kotlin/year2021/day02/day02_input.txt")
        .bufferedReader()
        .readLines()
        .map { line ->
            val (instruction, value) = line.split(" ")
            when (instruction) {
                "forward" -> Instruction.Forward(value.toInt())
                "up" -> Instruction.Up(value.toInt())
                "down" -> Instruction.Down(value.toInt())
                else -> Instruction.Forward(0)
            }
        }
    println(part1(lines))
    println(part2(lines))
}

sealed interface Instruction {
    val value: Int

    data class Forward(override val value: Int) : Instruction
    data class Up(override val value: Int) : Instruction
    data class Down(override val value: Int) : Instruction
}