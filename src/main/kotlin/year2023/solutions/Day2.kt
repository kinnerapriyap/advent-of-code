package year2023.solutions

import utils.setup.Day

fun main() = Day2().printDay()

class Day2 : Day(dayNumber = 2, year = 2023, useSampleInput = false) {
    private val input = inputList.map {
        val (number, rest) = """Game (\d+): (.*)""".toRegex().matchEntire(it)?.destructured!!
        val list = rest.split("; ").map { show ->
            show.split(", ").map { color ->
                color.split(" ").run {
                    Game.ShowColor.valueOf(get(1).uppercase()) to get(0).toInt()
                }
            }
        }
        Game(number.toInt(), list)
    }

    private val total = mapOf(Game.ShowColor.RED to 12, Game.ShowColor.GREEN to 13, Game.ShowColor.BLUE to 14)
    private fun isCorrect(list: List<Pair<Game.ShowColor, Int>>): Boolean {
        list.forEach { (color, num) ->
            if (total[color]!! < num) return false
        }
        return true
    }

    override fun partOne(): Any {
        return input.filter { it.shows.all { show -> isCorrect(show) } }.sumOf { it.number }
    }

    override fun partTwo(): Any {
        return input.sumOf {
            val ans = mutableMapOf(
                Game.ShowColor.RED to 0,
                Game.ShowColor.GREEN to 0,
                Game.ShowColor.BLUE to 0,
            )
            it.shows.forEach { show ->
                show.forEach { (color, num) ->
                    if (ans[color]!! < num) ans[color] = num
                }
            }
            ans.values.reduce { acc, i -> acc * i }
        }
    }

    data class Game(
        val number: Int,
        val shows: List<List<Pair<ShowColor, Int>>>
    ) {
        enum class ShowColor { BLUE, RED, GREEN }
    }
}