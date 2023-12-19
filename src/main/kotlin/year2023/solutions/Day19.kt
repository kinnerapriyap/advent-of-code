package year2023.solutions

import utils.setup.Day

class Day19 : Day(dayNumber = 19, year = 2023, useSampleInput = false) {
    data class Instruction(
        val condition: String?,
        val workflow: String
    ) {
        fun checkCondition(map: HashMap<Char, Int>): Boolean {
            val (f, num) = condition?.split(">", "<") ?: return true
            return if (condition.contains(">")) map[f[0]]!! > num.toInt()
            else map[f[0]]!! < num.toInt()
        }
    }

    private val input = inputString.split("\n\n")

    private val workflows = hashMapOf<String, List<Instruction>>()

    init {
        input[0].split("\n").map { str ->
            val (name, rest) = str.split("{")
            val (instructionsStr, _) = rest.split("}")
            val instructions = instructionsStr.split(",").map {
                val (condition, workflow) =
                    if (it.contains(":")) it.split(":")
                    else listOf(null, it)
                Instruction(condition, workflow!!)
            }
            workflows[name] = instructions
        }
    }

    private val ratings = input[1].split("\n").map { str ->
        val hashMap = hashMapOf<Char, Int>()
        str.removePrefix("{").removeSuffix("}").split(",")
            .forEach {
                val (f, num) = it.split("=")
                hashMap[f[0]] = num.toInt()
            }
        hashMap
    }

    override fun partOne(): Any {
        var ans = 0
        ratings.forEach { r ->
            var currWorkflow = "in"
            val visited = hashSetOf<String>()
            while (workflows.containsKey(currWorkflow) &&
                currWorkflow !in visited &&
                currWorkflow !in listOf("A", "R")
            ) {
                visited.add(currWorkflow)
                for (w in workflows[currWorkflow]!!) {
                    if (w.checkCondition(r)) {
                        currWorkflow = w.workflow
                        break
                    }
                }
            }
            if (currWorkflow == "A") ans += r.values.sum()
        }
        return ans
    }

    override fun partTwo(): Any {
        var answer = 0L
        fun recursive(currName: String, currHashMap: Map<Char, IntRange>) {
            if (currName == "A") {
                answer += currHashMap.values.fold(1L) { acc, range -> acc * range.count() }
            }
            val map = currHashMap.toMutableMap()
            workflows[currName]?.forEach {
                recursive(it.workflow, map.toMutableMap().apply { changeForCorrectCondition(it) })
                map.changeForWrongCondition(it)
            }
        }
        val forA = mutableMapOf(
            'x' to IntRange(1, 4000),
            'm' to IntRange(1, 4000),
            'a' to IntRange(1, 4000),
            's' to IntRange(1, 4000),
        )
        recursive("in", forA)
        return answer
    }

    private fun MutableMap<Char, IntRange>.changeForCorrectCondition(instruction: Instruction) {
        val (f, num) = instruction.condition?.split(">", "<") ?: return
        if (instruction.condition.contains(">")) this[f[0]] = IntRange(num.toInt() + 1, this[f[0]]!!.last)
        else this[f[0]] = IntRange(this[f[0]]!!.first, num.toInt() - 1)
    }

    private fun MutableMap<Char, IntRange>.changeForWrongCondition(instruction: Instruction) {
        val (f, num) = instruction.condition?.split(">", "<") ?: return
        if (instruction.condition.contains(">")) this[f[0]] = IntRange(this[f[0]]!!.first, num.toInt())
        else this[f[0]] = IntRange(num.toInt(), this[f[0]]!!.last)
    }
}