package year2023.solutions

import utils.setup.Day

class Day1 : Day(dayNumber = 1, year = 2023, useSampleInput = true) {
    override fun partOne(): Any =
        inputList.sumOf { str ->
            str.toList().run {
                (firstOrNull { it.isDigit() }?.digitToInt() ?: 0) * 10 +
                        (lastOrNull { it.isDigit() }?.digitToInt() ?: 0)
            }
        }

    override fun partTwo(): Any =
        inputList.sumOf { str ->
            extractDigits(str).toList().run {
                first().digitToInt() * 10 + last().digitToInt()
            }
        }

    private fun extractDigits(input: String): String = buildString {
        val digitWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        var current = ""
        input.forEach { char ->
            if (char.isLetter()) {
                current += char
                digitWords.find { it in current }?.let {
                    append(digitWords.indexOf(it) + 1)
                    current = it.last().toString()
                }
            } else if (char.isDigit()) {
                append(char)
                current = ""
            }
        }
    }

}