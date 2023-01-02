package year2022.day11

import java.io.File

fun part1(lines: List<Monkey>) = solve(rounds = 20, lines = lines, isManageable = true)

fun part2(lines: List<Monkey>) = solve(rounds = 10000, lines = lines, isManageable = false)

fun solve(rounds: Int, lines: List<Monkey>, isManageable: Boolean): ULong {
    val new = lines.toMutableList()
    val inspected = MutableList(lines.size) { 0 }
    val gcd = lines.fold(1UL) { acc, e -> acc * e.checkDivisible }
    for (i in 1..rounds) {
        new.forEach { monkey ->
            inspected[monkey.index] += monkey.items.size
            monkey.items.forEach { item ->
                var worryLevel = (item % gcd).doOperation(monkey.operation)
                if (isManageable) worryLevel /= 3UL
                if (worryLevel % monkey.checkDivisible == 0UL) new[monkey.throwTo.first].items.add(worryLevel)
                else new[monkey.throwTo.second].items.add(worryLevel)
            }
            monkey.items = mutableListOf()
        }
    }
    inspected.sortDescending()
    return inspected[0].toULong() * inspected[1].toULong()
}

fun ULong.doOperation(operation: String): ULong =
    when {
        operation.contains("old * old") -> this * this
        operation.contains("*") -> this * operation.substringAfter("* ").toULong()
        operation.contains("+") -> this + operation.substringAfter("+ ").toULong()
        else -> this
    }

fun main() {
    val lines = File("src/main/kotlin/year2022.day11/day11_input.txt")
        .bufferedReader()
        .readLines()
        .chunked(7) { list ->
            Monkey(
                index = list[0][7].digitToInt(),
                items = list[1].substringAfter(": ").split(", ").map { it.toULong() }.toMutableList(),
                operation = list[2].substringAfter("= "),
                checkDivisible = list[3].substringAfter("by ").toULong(),
                throwTo = list[4].substringAfter("monkey ").toInt() to
                        list[5].substringAfter("monkey ").toInt()
            )
        }
    // println(part1(lines))
    println(part2(lines))
}

data class Monkey(
    val index: Int,
    var items: MutableList<ULong>,
    val operation: String,
    val checkDivisible: ULong,
    val throwTo: Pair<Int, Int>,
)