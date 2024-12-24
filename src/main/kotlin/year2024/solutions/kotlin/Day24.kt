package year2024.solutions.kotlin

import utils.setup.Day

fun main() = Day24().printDay()

class Day24 : Day(dayNumber = 24, year = 2024, useSampleInput = false) {
    private val hashMap = HashMap<String, Int>()
    private val input = inputList.subList(inputList.indexOf("") + 1, inputList.size).map {
        val (s, ans) = it.split(" -> ")
        val (x, y, z) = s.split(" ")
        Instruction(
            x, z, ans, when (y) {
                "AND" -> Instruction.Op.AND
                "OR" -> Instruction.Op.OR
                "XOR" -> Instruction.Op.XOR
                else -> throw Exception("Invalid operation")
            }
        )
    }

    init {
        inputList.subList(0, inputList.indexOf("")).forEach { str ->
            val (x, y) = str.split(": ")
            hashMap[x] = y.toInt()
        }
    }

    override fun partOne(): Any {
        val q = input.toMutableList()
        while (q.isNotEmpty()) {
            val x = q.removeAt(0)
            if (hashMap.containsKey(x.x) && hashMap.containsKey(x.y)) {
                hashMap[x.ans] = when (x.op) {
                    Instruction.Op.AND -> hashMap[x.x]!! and hashMap[x.y]!!
                    Instruction.Op.OR -> hashMap[x.x]!! or hashMap[x.y]!!
                    Instruction.Op.XOR -> hashMap[x.x]!! xor hashMap[x.y]!!
                }
            } else q.add(x)
        }
        return hashMap.filter { it.key.contains('z') }.toList().sortedByDescending { it.first }
            .joinToString("") { it.component2().toString() }.chunked(5)
        //.toLong(2)
    }

    override fun partTwo(): Any {
        val x = hashMap.filter { it.key.contains('x') }.toList().sortedByDescending { it.first }
            .joinToString("") { it.component2().toString() }.toLong(2)
        val y = hashMap.filter { it.key.contains('y') }.toList().sortedByDescending { it.first }
            .joinToString("") { it.component2().toString() }.toLong(2)
        val z = (x + y).toString(2)
        println(z.chunked(5))

        val resolved = mutableMapOf<String, String>()
        fun resolve(value: String): String {
            if (value.startsWith("x") || value.startsWith("y")) return value
            return resolved[value] ?: value
        }

        val instructionMap = input.associateBy { it.ans } // Map for quick lookup
        val toResolve = input.map { it.ans }.toMutableSet()
        while (toResolve.isNotEmpty()) {
            val resolvedInThisPass = mutableSetOf<String>()
            for (key in toResolve) {
                val instr = instructionMap[key] ?: continue
                val xResolved = resolve(instr.x)
                val yResolved = resolve(instr.y)
                if (!xResolved.contains(Regex("[a-zA-Z]+")) && !yResolved.contains(Regex("[a-zA-Z]+"))) {
                    resolved[key] = "($xResolved ${instr.op} $yResolved)"
                    resolvedInThisPass.add(key)
                } else {
                    resolved[key] = "($xResolved ${instr.op} $yResolved)"
                }
            }
            toResolve.removeAll(resolvedInThisPass)
        }
        resolved.forEach { (key, value) ->
            println("$key = $value")
        }
        return 0
    }

    fun XYPair(first: String, second: String, ans: String): Boolean {
        val regex = """([xyz])(\d+)""".toRegex()
        val match1 = regex.matchEntire(first)
        val match2 = regex.matchEntire(second)
        val match3 = regex.matchEntire(ans)
        if (match1 != null && match2 != null && match3 != null) {
            val (letter1, number1) = match1.destructured
            val (letter2, number2) = match2.destructured
            val (letter3, number3) = match3.destructured
            return ((letter1 == "x" && letter2 == "y") || (letter1 == "y" && letter2 == "x")) && (number1 == number2) &&
                    (letter3 == "z") && (number3 == number1)
        }
        return false
    }

    private data class Instruction(val x: String, val y: String, val ans: String, val op: Op) {
        enum class Op { AND, OR, XOR }
    }
}