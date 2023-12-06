package year2021.solutions

import utils.data.hasAtIndex
import utils.setup.Day

fun main() = Day04().printDay()

class Day04 : Day(dayNumber = 4, year = 2021, useSampleInput = false) {

    private val input = inputString.split("\n\n")

    private val drawNumbers = input[0].split(",").mapNotNull { it.toIntOrNull() }

    private fun getBingoCards(): MutableList<MutableList<MutableList<Pair<Int, Boolean>>>> =
        input.subList(1, input.size).map { str ->
            str.split("\n").map { r ->
                r.split(" ").mapNotNull { it.toIntOrNull() }.map { it to false }.toMutableList()
            }.toMutableList()
        }.toMutableList()

    private fun MutableList<MutableList<MutableList<Pair<Int, Boolean>>>>.hasBingoCardWon(
        filter: List<Int> = emptyList()
    ): Int? {
        forEachIndexed { index, card ->
            if (card.any { r -> r.all { it.second } } && index !in filter)
                return index
            if (card[0].indices.any { cI ->
                    card.indices.all { card[it][cI].second }
                } && index !in filter)
                return index
        }
        return null
    }

    override fun partOne(): Any {
        val bingoCards = getBingoCards()
        drawNumbers.forEach { drawNumber ->
            bingoCards.forEachIndexed { index, card ->
                card.hasAtIndex(drawNumber to false)?.let {
                    bingoCards[index][it.row][it.col] =
                        bingoCards[index][it.row][it.col].first to true
                }
            }
            bingoCards.hasBingoCardWon()?.let { bingoCards[it] }?.let { winningCard ->
                return drawNumber * winningCard.flatten().filter { !it.second }.sumOf { it.first }
            }
        }
        return 0
    }

    override fun partTwo(): Any {
        val bingoCards = getBingoCards()
        val wins = BooleanArray(bingoCards.size) { false }
        drawNumbers.forEach { drawNumber ->
            bingoCards.forEachIndexed { index, card ->
                if (wins[index]) return@forEachIndexed
                card.hasAtIndex(drawNumber to false)?.let {
                    bingoCards[index][it.row][it.col] =
                        bingoCards[index][it.row][it.col].first to true
                }
            }
            bingoCards.hasBingoCardWon(wins.indices.filter { wins[it] })?.let { index ->
                wins[index] = true
                if (wins.all { it }) {
                    return drawNumber * bingoCards[index].flatten().filter { !it.second }.sumOf { it.first }
                }
            }
        }
        return 0
    }
}