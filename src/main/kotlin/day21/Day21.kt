package day21

import java.io.File

fun part1(lines: List<Monkey>): Long = lines.resolve().second.find { it.name == "root" }!!.number

const val ME = "humn"
fun part2(lines: List<Monkey>): Long {
    val root = lines.find { it.name == "root" }!! as Monkey.Wait
    val excludedMonkey = lines.filterIsInstance<Monkey.Wait>().find { it.monkeys.toList().contains(ME) }!!
    val (waits, shouts) = lines.resolve(excludedMonkey, root)
    waits.toList().forEach { monkey ->
        val first = shouts.find { it.name == monkey.monkeys.first }.let {
            if (it != null && it.name != ME) it.number else monkey.monkeys.first
        }.toString()
        val second = shouts.find { it.name == monkey.monkeys.second }.let {
            if (it != null && it.name != ME) it.number else monkey.monkeys.second
        }.toString()
        val changed = Monkey.Wait(monkey.name, first to second, monkey.sign)
        waits.remove(monkey)
        waits.add(changed)
    }
    val ordered = mutableListOf(waits.find { it.name == excludedMonkey.name }!!)
    while (ordered.size != waits.size) {
        ordered.add(waits.find { monkey -> monkey.monkeys.toList().any { it == ordered.last().name } }!!)
    }
    return ordered.foldRight(shouts.find { it.name == root.monkeys.second }!!.number) { monkey, rhs ->
        monkey.reverseSignAndCalculate(rhs)
    }
}

fun List<Monkey>.resolve(
    excludedMonkey: Monkey? = null,
    toRemove: Monkey? = null
): Pair<MutableList<Monkey.Wait>, MutableList<Monkey.Shout>> {
    val shouts = filterIsInstance<Monkey.Shout>().toMutableList()
    val waits = filterIsInstance<Monkey.Wait>().toMutableList().apply { remove(toRemove) }
    while (waits.isNotEmpty()) {
        val old = waits.toList()
        old.forEach { monkey ->
            if (excludedMonkey != null && monkey == excludedMonkey) return@forEach
            (monkey.convertToShoutIfPossible(shouts) as? Monkey.Shout)?.let {
                waits.remove(monkey)
                shouts.add(it)
            }
        }
        if (waits == old) break
    }
    return waits to shouts
}

fun main() {
    val lines = File("src/main/kotlin/day21/day21_input.txt")
        .bufferedReader()
        .readLines()
        .map { line ->
            val (name, rest) = line.split(": ")
            if (rest.toLongOrNull() != null) Monkey.Shout(name, rest.toLong())
            else rest.split(" ").let { Monkey.Wait(name, it[0] to it[2], it[1][0]) }
        }
    println(part1(lines))
    println(part2(lines))
}

sealed interface Monkey {
    val name: String

    data class Shout(override val name: String, val number: Long) : Monkey
    data class Wait(override val name: String, val monkeys: Pair<String, String>, val sign: Char) : Monkey {
        fun convertToShoutIfPossible(monkeyList: List<Shout>): Monkey {
            val firstMonkey = monkeyList.find { it.name == monkeys.first } ?: return this
            val secondMonkey = monkeyList.find { it.name == monkeys.second } ?: return this
            return Shout(
                name,
                when (sign) {
                    '+' -> firstMonkey.number + secondMonkey.number
                    '-' -> firstMonkey.number - secondMonkey.number
                    '*' -> firstMonkey.number * secondMonkey.number
                    '/' -> firstMonkey.number / secondMonkey.number
                    else -> -1
                }
            )
        }

        fun reverseSignAndCalculate(rhs: Long): Long {
            val isFirstMonkey = monkeys.first.toLongOrNull() == null
            val monkey = (if (isFirstMonkey) monkeys.second else monkeys.first).toLong()
            return when (sign) {
                '+' -> rhs - monkey
                '-' -> if (isFirstMonkey) rhs + monkey else monkey - rhs
                '*' -> rhs / monkey
                '/' -> if (isFirstMonkey) rhs * monkey else monkey / rhs
                else -> rhs + monkey
            }
        }
    }
}