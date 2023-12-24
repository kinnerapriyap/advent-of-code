package year2023.solutions

import utils.setup.Day

@OptIn(ExperimentalStdlibApi::class)
class Day24 : Day(dayNumber = 24, year = 2023, useSampleInput = false) {
    data class Pt3DL(val x: Double, val y: Double, val z: Double)
    data class Vel3DL(val x: Double, val y: Double, val z: Double)

    private val input = inputList.map { str ->
        val (p, v) = str.split("@").map { s ->
            s.split(",").map { it.trim().toDouble() }
        }
        Pt3DL(p[0], p[1], p[2]) to Vel3DL(v[0], v[1], v[2])
    }

    private val testLimits = 7.0..27.0
    private val limits = 200000000000000.0..400000000000000.0

    //(b1c2−b2c1/a1b2−a2b1,c1a2−c2a1/a1b2−a2b1)
    override fun partOne(): Any {
        return (0..<input.lastIndex).map { oneIndex ->
            val (oneP, oneV) = input[oneIndex]
            (oneIndex + 1..input.lastIndex).filter { twoIndex ->
                val (twoP, twoV) = input[twoIndex]
                //println("$oneP, $oneV | $twoP, $twoV")
                val denominator = oneV.x * twoV.y - oneV.y * twoV.x
                if (denominator == 0.0) return@filter false
                val pointOfIntersectionX =
                    (((oneP.y - twoP.y) * oneV.x * twoV.x) +
                            (oneV.x * twoV.y * twoP.x) -
                            (oneV.y * twoV.x * oneP.x)) / denominator
                val pointOfIntersectionY =
                    (((twoP.x - oneP.x) * oneV.y * twoV.y) +
                            (oneV.x * twoV.y * oneP.y) -
                            (oneV.y * twoV.x * twoP.y)) / denominator
                //println("POI=$pointOfIntersectionX,$pointOfIntersectionY")
                val timeOne = (pointOfIntersectionX - oneP.x) / oneV.x
                val timeTwo = (pointOfIntersectionX - twoP.x) / twoV.x
                if (timeOne < 0 || timeTwo < 0) return@filter false
                pointOfIntersectionX in limits && pointOfIntersectionY in limits
            }.count()
        }.sum()
    }

    override fun partTwo(): Any {
        return 0
    }
}