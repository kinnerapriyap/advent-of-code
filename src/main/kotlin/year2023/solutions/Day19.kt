package year2023.solutions

import utils.setup.Day

fun main() = Day19().printDay()

class Day19 : Day(dayNumber = 19, year = 2023, useSampleInput = true) {
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

    private fun HashMap<Char, IntRange>.changeForCorrectCondition(instruction: Instruction) {
        val (f, num) = instruction.condition?.split(">", "<") ?: return
        if (instruction.condition.contains(">")) this[f[0]] = IntRange(num.toInt() + 1, this[f[0]]!!.last)
        else this[f[0]] = IntRange(this[f[0]]!!.first, num.toInt() - 1)
    }

    private fun HashMap<Char, IntRange>.changeForWrongCondition(instruction: Instruction) {
        val (f, num) = instruction.condition?.split(">", "<") ?: return
        if (instruction.condition.contains(">")) this[f[0]] = IntRange(this[f[0]]!!.first, num.toInt())
        else this[f[0]] = IntRange(num.toInt(), this[f[0]]!!.last)
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
        val ans = mutableListOf<HashMap<Char, IntRange>>()
        workflows
            .filter { (_, ins) -> ins.any { it.workflow == "A" } }
            .forEach { (name, ins) ->
                val indexes = ins.mapIndexed { index, instruction -> index to instruction }
                    .filter { it.second.workflow == "A" }
                indexes.forEach { (ind, i) ->
                    ans += solve(name, ind)
                }
            }

        return ans.joinToString("\n")
    }

    private fun solve(workflowName: String = "rfg", indexA: Int = 2): List<HashMap<Char, IntRange>> {
        val end = "in"
        val answers = mutableListOf<HashMap<Char, IntRange>>()
        fun recursive(currName: String, indexOfCurr: Int, currHashMap: HashMap<Char, IntRange>) {
            val workflowInstructions = workflows[currName]!!
            val iBefore = workflowInstructions.subList(0, indexOfCurr)
            currHashMap.changeForCorrectCondition(workflowInstructions[indexOfCurr])
            iBefore.forEach { currHashMap.changeForWrongCondition(it) }
            if (currName == end) {
                answers.add(currHashMap)
                return
            }
            workflows
                .filter { it.key != currName && it.value.any { it.workflow == currName } }
                .forEach { (hasCurrName, hasCurrInstructions) ->
                    val indexes = hasCurrInstructions.mapIndexed { index, instruction -> index to instruction }
                        .filter { it.second.workflow == currName }
                    indexes.forEach { (indexCurr, _) ->
                        recursive(hasCurrName, indexCurr, currHashMap)
                    }
                }
        }

        val forA = hashMapOf(
            'x' to IntRange(1, 4000),
            'm' to IntRange(1, 4000),
            'a' to IntRange(1, 4000),
            's' to IntRange(1, 4000),
        )
        recursive(workflowName, indexA, forA)
        return answers
    }
}