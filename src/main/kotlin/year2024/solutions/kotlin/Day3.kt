import utils.setup.Day

fun main() = Day3().printDay()

class Day3 : Day(dayNumber = 3, year = 2024, useSampleInput = true) {
    private val input = inputString

    override fun partOne(): Any {
        val pattern = Regex("""mul\((\d+),(\d+)\)""")
        var total = 0
        pattern.findAll(input).forEach { matchResult ->
            total += matchResult.groupValues[1].toInt() * matchResult.groupValues[2].toInt()
        }
        return total
    }

    override fun partTwo(): Any {
        val secondPattern = Regex("""(mul\((\d+,\d+)\)|(don't|do)\(\))""")
        var enabled = true
        val canBeEvaluated = mutableListOf<MatchResult>()
        secondPattern.findAll(input).forEach { matchResult ->
            when {
                matchResult.value == "do()" -> enabled = true
                matchResult.value == "don't()" -> enabled = false
                matchResult.value.startsWith("mul(") && enabled -> canBeEvaluated.add(matchResult)
            }
        }
        var total = 0
        canBeEvaluated.forEach { matchResult ->
            val (x, y) = matchResult.groupValues[2].split(",").map { it.toInt() }
            total += x * y
        }
        return total
    }
}