package year2024.solutions.kotlin

import utils.setup.Day

fun main() = Day5().printDay()

class Day5 : Day(dayNumber = 5, year = 2024, useSampleInput = false) {
    val i = inputList.indexOf("")
    val hashMap = hashMapOf<Int, MutableList<Int>>()

    init {
        val a = inputList.subList(0, i).map {
            val x = it.split("|")
            x[0].toInt() to x[1].toInt()
        }
        a.forEach { (before, after) ->
            hashMap[before] = hashMap[before]?.toMutableList()?.apply { add(after) } ?: mutableListOf(after)
        }
    }

    private val lists = inputList.subList(i + 1, inputList.size).map { string -> string.split(",").map { it.toInt() } }

    override fun partOne(): Any {
        var count = 0
        lists.forEach { list ->
            for (i in list.indices) {
                for (b in i + 1 until list.size) {
                    if (hashMap[list[b]]?.contains(list[i]) == true) return@forEach
                }
            }
            count += list[list.size / 2]
        }
        return count
    }

    override fun partTwo(): Any {
        var count = 0
        lists.forEach { list ->
            var isValid = true
            for (i in list.indices) {
                for (b in i + 1 until list.size) {
                    if (hashMap[list[b]]?.contains(list[i]) == true) {
                        isValid = false
                        break
                    }
                }
                if (!isValid) break
            }

            if (!isValid) {
                val fixedList = list.sortedWith { o1, o2 -> if (hashMap[o1]?.contains(o2) == true) -1 else 1 }
                count += fixedList[fixedList.size / 2]
            }
        }
        return count
    }
}