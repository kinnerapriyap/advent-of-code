package day13

import java.io.File
import java.util.*

fun part1(lines: List<Nest>): Int =
    lines.chunked(2)
        .foldIndexed(0) { index, acc, (left, right) ->
            if (left <= right) acc + index + 1 else acc
        }

fun part2(lines: List<Nest>): Int {
    val divider1 = "[[2]]".parseToNest()
    val divider2 = "[[6]]".parseToNest()
    return lines.toMutableList().apply { addAll(listOf(divider1, divider2)) }
        .sorted()
        .foldIndexed(1) { index, acc, nest ->
            if (nest == divider1 || nest == divider2) acc * (index + 1)
            else acc
        }
}

fun main() {
    val lines = File("src/main/kotlin/day13/day13_input.txt")
        .bufferedReader()
        .readLines()
        .filter { it.isNotEmpty() }
        .map { it.parseToNest() }
    println(part1(lines))
    println(part2(lines))
}

fun String.parseToNest(): Nest {
    val stack = Stack<NestList>()
    var output = NestList()
    var moreDigits: Int? = null
    for (char in this) {
        when {
            char.isDigit() -> moreDigits = (moreDigits ?: 0) * 10 + char.digitToInt()
            char == ',' -> moreDigits?.let {
                output.values.add(NestNumber(it))
                moreDigits = null
            }

            char == ']' -> {
                moreDigits?.let {
                    output.values.add(NestNumber(it))
                    moreDigits = null
                }
                output = stack.pop().apply { values.add(output) }
            }

            char == '[' -> {
                stack.push(output)
                output = NestList()
            }
        }
    }
    return output
}

data class NestNumber(val value: Int) : Nest {
    override fun toString(): String = "$value"
}

data class NestList(val values: MutableList<Nest> = mutableListOf()) : Nest {
    override fun toString(): String = "$values"
}

sealed interface Nest : Comparable<Nest> {
    override fun compareTo(other: Nest): Int =
        when (this) {
            is NestNumber -> {
                when (other) {
                    is NestNumber -> value.compareTo(other.value)
                    is NestList -> NestList(mutableListOf<Nest>().apply { add(NestNumber(value)) }).compareTo(other)
                }
            }

            is NestList -> {
                when (other) {
                    is NestNumber -> compareTo(NestList(mutableListOf<Nest>().apply { add(NestNumber(other.value)) }))
                    is NestList -> {
                        var output = 0
                        for ((left, right) in values.zip(other.values)) {
                            val result = left.compareTo(right)
                            if (result != 0) {
                                output = result
                                break
                            }
                        }
                        if (output != 0) output
                        else values.size - other.values.size
                    }
                }
            }
        }
}