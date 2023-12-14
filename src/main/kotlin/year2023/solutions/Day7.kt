package year2023.solutions

import utils.setup.Day

class Day7 : Day(dayNumber = 7, year = 2023, useSampleInput = false) {
    private val input = inputList.map { str ->
        str.split(" ").let { it[0].toList() to it[1].trim().toInt() }
    }

    private fun getComparator(order: List<Char>) = Comparator<Seven> { o1, o2 ->
        o1.hand.priority.compareTo(o2.hand.priority).let {
            if (it == 0) {
                o1.card.zip(o2.card).forEach { (one, two) ->
                    if (order.indexOf(one) > order.indexOf(two)) return@Comparator -1
                    else if (order.indexOf(one) < order.indexOf(two)) return@Comparator 1
                }
                0
            } else it
        }
    }

    override fun partOne(): Any {
        return input.map { Seven(card = it.first, hand = it.first.getHand(), bid = it.second) }
            .sortedWith(getComparator(listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')))
            .mapIndexed { index, seven -> seven.bid * (index + 1) }.sum()
    }

    override fun partTwo(): Any {
        return input
            .map { Seven(card = it.first, hand = it.first.getHand(hasWildcard = true), bid = it.second) }
            .sortedWith(getComparator(listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')))
            .mapIndexed { index, seven -> seven.bid * (index + 1) }.sum()
    }

    private fun List<Char>.getHand(hasWildcard: Boolean = false): Seven.Hand =
        groupBy { it }.mapValues { it.value.count() }.toMutableMap().run {
            val j = if (hasWildcard) remove('J') ?: 0 else 0
            if (j == 5) return@run Seven.Hand.FIVE
            values.sortedDescending().run {
                fun f(n: Int) = if (j > n - get(0)) j - n + get(0) else 0
                when {
                    get(0) + j >= 5 -> Seven.Hand.FIVE
                    get(0) + j >= 4 -> Seven.Hand.FOUR
                    get(0) + j >= 3 && get(1) + f(3) == 2 -> Seven.Hand.FULL
                    get(0) + j >= 3 -> Seven.Hand.THREE
                    get(0) + j >= 2 && get(1) + f(2) == 2 -> Seven.Hand.TWO
                    get(0) + j >= 2 -> Seven.Hand.ONE
                    else -> Seven.Hand.HIGH
                }
            }
        }

    data class Seven(val card: List<Char>, val hand: Hand, val bid: Int) {
        enum class Hand(val priority: Int) {
            FIVE(6), FOUR(5), FULL(4), THREE(3), TWO(2), ONE(1), HIGH(0)
        }
    }
}