package year2024.solutions.kotlin

import utils.data.DirectionOrtho
import utils.data.Point
import utils.data.get
import utils.data.hasAtIndex
import utils.data.right
import utils.data.toDelta
import utils.setup.Day

fun main() = Day6().printDay()

class Day6 : Day(dayNumber = 6, year = 2024, useSampleInput = true) {
    private var input = inputList.map { str -> str.toCharArray() }
    val hashSet = hashSetOf<Point>()
    var pd = getPointAndDir()

    override fun partOne(): Any {
        var (p, dir) = pd
        while (input.get(p!!.add(dir.toDelta())) != null) {
            hashSet.add(p)
            if (input.get(p.add(dir.toDelta())) != '#') p = p.add(dir.toDelta())
            else dir = dir.right()
        }
        return hashSet.count() + 1
    }

    override fun partTwo(): Any {
        var c = 0
        hashSet.forEach { t -> if (isLoop(t)) c++ }
        return c
    }

    private fun isLoop(t: Point): Boolean {
        val new = inputList.map { str -> str.toCharArray() }.apply { this[t.row][t.col] = '#' }
        var (newP, newDir) = pd
        val visited = mutableListOf<Pair<Point, DirectionOrtho>>()
        while (new.get(newP!!.add(newDir.toDelta())) != null) {
            if (visited.contains(newP to newDir)) return true
            visited.add(newP to newDir)
            if (new.get(newP.add(newDir.toDelta())) != '#') newP = newP.add(newDir.toDelta())
            else newDir = newDir.right()
        }
        return false
    }

    private fun getPointAndDir() = if (input.hasAtIndex('^') != null) {
        input.hasAtIndex('^') to DirectionOrtho.UP
    } else if (input.hasAtIndex('>') != null) {
        input.hasAtIndex('>') to DirectionOrtho.RIGHT
    } else if (input.hasAtIndex('v') != null) {
        input.hasAtIndex('v') to DirectionOrtho.DOWN
    } else {
        input.hasAtIndex('<') to DirectionOrtho.LEFT
    }
}