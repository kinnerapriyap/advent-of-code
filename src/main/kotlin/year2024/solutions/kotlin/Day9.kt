package year2024.solutions.kotlin

import utils.setup.Day

fun main() = Day9().printDay()

class Day9 : Day(dayNumber = 9, year = 2024, useSampleInput = false) {
    private val input = inputString.toCharArray().map { ch -> ch.toString().toInt() }

    override fun partOne(): Any {
        val ans = mutableListOf<Int>()
        input.forEachIndexed { index, i ->
            if (index % 2 == 0) repeat(i) { ans.add(index / 2) }
            else repeat(i) { ans.add(-1) }
        }
        var low = 0
        while (true) {
            while (low < ans.size && ans[low] != -1) low++
            if (low >= ans.size) break
            val x = ans[low]
            ans[low] = ans[ans.lastIndex]
            ans[ans.lastIndex] = x
            ans.removeLast()
        }
        var c = 0L
        for (i in ans.indices) {
            if (ans[i] != -1) c += i * ans[i].toLong()
        }
        return c
    }

    override fun partTwo(): Any {
        val ans = mutableListOf<Int>()
        val dotBlocks = mutableListOf<IntRange>()
        val fileBlocks = mutableListOf<IntRange>()
        var dettol = 0
        input.forEachIndexed { index, i ->
            if (index % 2 == 0) {
                repeat(i) { ans.add(index / 2) }
                fileBlocks.add(dettol until dettol + i)
            } else {
                repeat(i) { ans.add(-1) }
                dotBlocks.add(dettol until dettol + i)
            }
            dettol += i
        }
        fileBlocks.reversed().forEach { t ->
            val size = t.last - t.first + 1
            for (d in dotBlocks) {
                val size2 = d.last - d.first + 1
                if (size2 >= size) {
                    for (i in 0 until size) {
                        ans[d.first + i] = ans[t.first + i]
                        ans[t.first + i] = -1
                    }
                    val o = dotBlocks.indexOf(d)
                    dotBlocks.remove(d)
                    if (size2 > size) dotBlocks.add(o, d.first + size until d.last + 1)
                    break
                }
            }
        }
        var c = 0L
        for (i in ans.indices) {
            if (ans[i] != -1) c += i * ans[i].toLong()
        }
        return c
    }
}