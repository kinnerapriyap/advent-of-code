package year2024.solutions.kotlin

import utils.data.DirectionOrtho
import utils.data.Point
import utils.data.get
import utils.data.hasAtIndex
import utils.data.move
import utils.data.printGridl
import utils.data.toDirectionOrtho
import utils.setup.Day

fun main() = Day15().printDay()

class Day15 : Day(dayNumber = 15, year = 2024, useSampleInput = false) {
    private val directions = inputList.subList(inputList.indexOf("") + 1, inputList.size)
        .flatMap { it.split("") }.filterNot { it.isBlank() }.map { it[0].toDirectionOrtho() }

    override fun partOne(): Any {
        val map = inputList.subList(0, inputList.indexOf("")).map { it.toCharArray() }
        var robot = map.hasAtIndex('@')
        directions.forEach { dir ->
            dir.move(robot!!).let { next ->
                if (map.get(next) == '.') {
                    map[robot!!.row][robot!!.col] = '.'
                    map[next.row][next.col] = '@'
                    robot = Point(next.row, next.col)
                } else if (map.get(next) == 'O') {
                    // push Os until the next .
                    var n = next.move(dir)
                    while (map.get(n) == 'O') n = dir.move(n)
                    if (map.get(n) == '.') {
                        map[robot!!.row][robot!!.col] = '.'
                        map[next.row][next.col] = '@'
                        map[n.row][n.col] = 'O'
                        robot = Point(next.row, next.col)
                    }
                }
            }
        }
        var c = 0
        map.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, char ->
                if (char == 'O') c += row * 100 + col
            }
        }
        return c
    }

    override fun partTwo(): Any {
        val map = inputList.subList(0, inputList.indexOf("")).map { it.toMutableList() }
        map.forEach { row ->
            var i = 0
            while (i < row.size) {
                when (row[i]) {
                    'O' -> {
                        row[i] = '['
                        row.add(i + 1, ']')
                    }

                    '@' -> {
                        row[i] = '@'
                        row.add(i + 1, '.')
                    }

                    '.' -> {
                        row[i] = '.'
                        row.add(i + 1, '.')
                    }

                    '#' -> {
                        row[i] = '#'
                        row.add(i + 1, '#')
                    }
                }
                i += 2
            }
        }
        var robot = map.hasAtIndex('@')
        directions.forEach { dir ->
            dir.move(robot!!).let { next ->
                if (map.get(next) == '.') {
                    map[robot!!.row][robot!!.col] = '.'
                    map[next.row][next.col] = '@'
                    robot = Point(next.row, next.col)
                } else if (map.get(next) in listOf('[', ']')) {
                    println(dir)
                    when (dir) {
                        DirectionOrtho.UP -> {
                            // move all connected [ and ] up by one place
                            val other =
                                if (map.get(next) == '[') next.move(DirectionOrtho.RIGHT)
                                else next.move(DirectionOrtho.LEFT)
                            val connectedBrackets = mutableSetOf<Point>(next, other)
                            val current = mutableListOf<Point>(next, other)
                            val checked = mutableSetOf<Point>(next, other)
                            while (current.isNotEmpty()) {
                                var check = current.removeFirst()
                                check = check.move(dir)
                                if (map.get(check) in listOf(']', '[')) {
                                    val other =
                                        if (map.get(check) == '[') check.move(DirectionOrtho.RIGHT)
                                        else check.move(DirectionOrtho.LEFT)
                                    if (check !in checked) {
                                        current.add(check)
                                        checked.add(check)
                                    }
                                    if (other !in checked) {
                                        current.add(other)
                                        checked.add(other)
                                    }
                                    connectedBrackets.add(check)
                                    connectedBrackets.add(other)
                                }
                            }
                            println(connectedBrackets)
                            val rowAboveNO = connectedBrackets.any { map.get(Point(it.row - 1, it.col)) == '#' }
                            if (!rowAboveNO) {
                                connectedBrackets.sortedBy { it.row }.forEach {
                                    map[it.row - 1][it.col] = map[it.row][it.col]
                                    map[it.row][it.col] = '.'
                                }
                                map[robot!!.row][robot!!.col] = '.'
                                robot = Point(next.row, next.col)
                                map[robot!!.row][robot!!.col] = '@'
                            }
                        }

                        DirectionOrtho.DOWN -> {
                            // move all connected [ and ] down by one place
                            val other =
                                if (map.get(next) == '[') next.move(DirectionOrtho.RIGHT)
                                else next.move(DirectionOrtho.LEFT)
                            val connectedBrackets = mutableSetOf<Point>(next, other)
                            val current = mutableListOf<Point>(next, other)
                            val checked = mutableSetOf<Point>(next, other)
                            while (current.isNotEmpty()) {
                                var check = current.removeFirst()
                                check = check.move(dir)
                                if (map.get(check) in listOf(']', '[')) {
                                    val other =
                                        if (map.get(check) == '[') check.move(DirectionOrtho.RIGHT)
                                        else check.move(DirectionOrtho.LEFT)
                                    if (check !in checked) {
                                        current.add(check)
                                        checked.add(check)
                                    }
                                    if (other !in checked) {
                                        current.add(other)
                                        checked.add(other)
                                    }
                                    connectedBrackets.add(check)
                                    connectedBrackets.add(other)
                                }
                            }
                            println(connectedBrackets)
                            val rowBelowNO = connectedBrackets.any { map.get(Point(it.row + 1, it.col)) == '#' }
                            if (!rowBelowNO) {
                                connectedBrackets.sortedByDescending { it.row }.forEach {
                                    map[it.row + 1][it.col] = map[it.row][it.col]
                                    map[it.row][it.col] = '.'
                                }
                                map[robot!!.row][robot!!.col] = '.'
                                robot = Point(next.row, next.col)
                                map[robot!!.row][robot!!.col] = '@'
                            }
                        }

                        DirectionOrtho.RIGHT -> {
                            if (map.get(next) == '[') {
                                var n = next.move(dir)
                                while (map.get(n) == '[' || map.get(n) == ']') n = dir.move(n)
                                if (map.get(n) == '.') {
                                    // move everything in the row from next to n by one place
                                    for (i in n.col downTo next.col - 1) {
                                        map[next.row][i] = map[next.row][i - 1]
                                    }
                                    map[robot!!.row][robot!!.col] = '.'
                                    robot = Point(next.row, next.col)
                                    map[robot!!.row][robot!!.col] = '@'
                                }
                            }
                        }

                        DirectionOrtho.LEFT -> {
                            if (map.get(next) == ']') {
                                var n = next.move(dir)
                                while (map.get(n) == '[' || map.get(n) == ']') n = dir.move(n)
                                if (map.get(n) == '.') {
                                    // move everything in the row from next to n by one place
                                    for (i in n.col..next.col + 1) {
                                        map[next.row][i] = map[next.row][i + 1]
                                    }
                                    map[robot!!.row][robot!!.col] = '.'
                                    robot = Point(next.row, next.col)
                                    map[robot.row][robot.col] = '@'
                                }
                            }
                        }
                    }
                }
            }
        }
        map.printGridl()
        var c = 0
        map.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, char ->
                if (char == '[') c += row * 100 + col
            }
        }
        return c
    }
}