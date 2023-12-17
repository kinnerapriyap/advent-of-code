package year2023.solutions

import utils.data.DirectionOrtho
import utils.data.Point
import utils.data.findShortestPathByPredicate
import utils.data.get
import utils.data.left
import utils.data.move
import utils.data.right
import utils.setup.Day

class Day17 : Day(dayNumber = 17, year = 2023, useSampleInput = true) {
    private val input = inputList.map { str -> str.map { it.digitToInt() } }
    private val startOne = State(Point(0, 0), DirectionOrtho.RIGHT, 0)
    private val startTwo = State(Point(0, 0), DirectionOrtho.DOWN, 0)
    private val end = Point(input.lastIndex, input[0].lastIndex)

    override fun partOne(): Any {
        return getMin(startOne, 0, 3)
    }

    override fun partTwo(): Any {
        return minOf(
            getMin(startOne, 4, 10),
            getMin(startTwo, 4, 10)
        )
    }

    private fun getMin(start: State, min: Int, max: Int) =
        findShortestPathByPredicate(
            start,
            { it.point == end && it.until >= min },
            { state -> state.next(min, max).filter { input.get(it.point) != null } },
            { _, (point) -> input.get(point)!! })
            .getScore()

    data class State(val point: Point, val dir: DirectionOrtho, val until: Int)

    private fun State.next(min: Int, max: Int): List<State> = buildList {
        if (until < max) add(State(dir.move(point), dir, until + 1))
        if (until >= min) {
            add(State(dir.right().move(point), dir.right(), 1))
            add(State(dir.left().move(point), dir.left(), 1))
        }
    }
}