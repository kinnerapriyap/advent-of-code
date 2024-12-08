package utils.setup

import java.io.BufferedReader
import java.io.File

object InputReader {

    fun getInputAsString(day: Int, year: Int, useSampleInput: Boolean): String =
        fileReader(day, year, useSampleInput).readText()

    fun getInputAsList(day: Int, year: Int, useSampleInput: Boolean): List<String> =
        fileReader(day, year, useSampleInput).readLines()

    private fun fileReader(day: Int, year: Int, useSampleInput: Boolean): BufferedReader =
        (if (useSampleInput) "day${day}_input_test" else "input").let {
            File("src/main/kotlin/year$year/inputs/$it.txt")
                .bufferedReader()
        }
}