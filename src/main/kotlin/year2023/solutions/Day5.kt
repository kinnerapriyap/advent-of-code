package year2023.solutions

import utils.setup.Day

fun main() = Day5().printDay()

class Day5 : Day(dayNumber = 5, year = 2023, useSampleInput = false) {
    private val input = inputString.split("\n\n").map { str ->
        str.splitToSequence("\n")
            .map { it.split(" ").mapNotNull { x -> x.toLongOrNull() } }
            .filterNot { it.isEmpty() }.toList()
    }

    override fun partOne(): Any {
        val inputMaps = input.subList(1, input.size).map { inputType ->
            inputType.map { (d, s, r) -> s until s + r to d - s }
        }
        return input[0].first().minOfOrNull { n ->
            inputMaps.fold(n) { acc, pairs -> pairs.getNumber(acc) }
        } ?: 0
    }

    override fun partTwo(): Any {
        val inputMaps = input.subList(1, input.size).map { inputType ->
            inputType.map { (d, s, r) -> s until s + r to d - s }
        }
        return input[0][0].chunked(2)
            .minOf { (start, size) ->
                (start until start + size).minOf { n ->
                    inputMaps.fold(n) { acc, pairs -> pairs.getNumber(acc) }
                }
            }
    }

    private fun List<Pair<LongRange, Long>>.getNumber(num: Long): Long =
        firstOrNull { it.first.contains(num) }?.let { num + it.second } ?: num
}
