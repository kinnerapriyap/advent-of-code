package utils.setup

abstract class Day(
    private val dayNumber: Int,
    private val year: Int,
    private val useSampleInput: Boolean = true
) {
    val inputList: List<String> by lazy {
        InputReader.getInputAsList(dayNumber, year, useSampleInput)
    }
    val inputString: String by lazy {
        InputReader.getInputAsString(dayNumber, year, useSampleInput)
    }

    abstract fun partOne(): Any
    abstract fun partTwo(): Any
    fun printDay() {
        val header = "Day $dayNumber, $year"
        val footer = "—".repeat(header.length)

        println(header)
        println("Part 1: ${partOne()}")
        println("Part 2: ${partTwo()}")
        println(footer)
    }
}