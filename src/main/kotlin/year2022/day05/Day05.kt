package year2022.day05

import java.io.File
import java.util.*

fun part1(stacks: List<Stack<Char>>, instructions: List<Triple<Int, Int, Int>>): String {
    instructions.forEach { (noOfCrates, from, to) ->
        for (i in 0 until noOfCrates) stacks[to].push(stacks[from].pop())
    }
    return stacks.fold("") { sum, element ->
        sum + element.pop().toString()
    }
}

fun part2(stacks: List<Stack<Char>>, instructions: List<Triple<Int, Int, Int>>): String {
    instructions.forEach { (noOfCrates, from, to) ->
        val l = Stack<Char>()
        for (i in 0 until noOfCrates) l.push(stacks[from].pop())
        while (l.isNotEmpty()) stacks[to].push(l.pop())
    }
    return stacks.fold("") { sum, element ->
        sum + element.pop().toString()
    }
}

fun main() {
    val lines = File("src/main/kotlin/year2022.day05/day5_input.txt")
        .bufferedReader()
        .readLines()
    val stacks = List(9) { Stack<Char>() }.apply {
        lines.take(8).reversed()
            .map { it.chunked(4) }
            .forEach { row ->
                row.forEachIndexed { index, s ->
                    if (s.isNotBlank()) this[index].push(s[1])
                }
            }
    }
    val instructions = mutableListOf<Triple<Int, Int, Int>>().apply {
        lines.drop(10)
            .map { it.split(" ").mapNotNull { piece -> piece.toIntOrNull() } }
            .forEach { (noOfCrates, from, to) ->
                add(Triple(noOfCrates, from - 1, to - 1))
            }
    }
    //println(part1(stacks, instructions))
    println(part2(stacks, instructions))
}