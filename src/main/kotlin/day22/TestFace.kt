package day22

import day22.Direction.DOWN
import day22.Direction.UP
import day22.Direction.RIGHT
import day22.Direction.LEFT

data class TestEdge(
    val face: TestFace,
    val direction: Direction,
    val isUlta: Boolean
)

fun findTestFace(row: Int, col: Int): TestFace =
    TestFace.values().find {
        row in it.startRow until it.startRow + testSize &&
                col in it.startCol until it.startCol + testSize
    }!!

enum class TestFace(val startRow: Int, val startCol: Int) {
    FACE_1(0, testSize * 2),
    FACE_2(testSize, 0),
    FACE_3(testSize, testSize),
    FACE_4(testSize, testSize * 2),
    FACE_5(testSize * 2, testSize * 2),
    FACE_6(testSize * 2, testSize * 3);

    companion object {
        fun TestFace.getEdges(): List<TestEdge> =
            when (this) {
                FACE_1 -> listOf(
                    TestEdge(FACE_2, DOWN, true),
                    TestEdge(FACE_6, LEFT, true),
                    TestEdge(FACE_4, DOWN, false),
                    TestEdge(FACE_3, DOWN, false)
                )

                FACE_2 -> listOf(
                    TestEdge(FACE_1, DOWN, true),
                    TestEdge(FACE_3, RIGHT, true),
                    TestEdge(FACE_5, UP, false),
                    TestEdge(FACE_6, UP, false)
                )

                FACE_3 -> listOf(
                    TestEdge(FACE_1, RIGHT, false),
                    TestEdge(FACE_4, RIGHT, false),
                    TestEdge(FACE_5, RIGHT, true),
                    TestEdge(FACE_2, LEFT, false)
                )

                FACE_4 -> listOf(
                    TestEdge(FACE_1, UP, false),
                    TestEdge(FACE_6, DOWN, true),
                    TestEdge(FACE_5, DOWN, false),
                    TestEdge(FACE_3, LEFT, false)
                )

                FACE_5 -> listOf(
                    TestEdge(FACE_4, UP, false),
                    TestEdge(FACE_6, RIGHT, false),
                    TestEdge(FACE_2, UP, true),
                    TestEdge(FACE_3, UP, true)
                )

                FACE_6 -> listOf(
                    TestEdge(FACE_4, LEFT, true),
                    TestEdge(FACE_1, LEFT, true),
                    TestEdge(FACE_2, RIGHT, true),
                    TestEdge(FACE_5, LEFT, false)
                )
            }
    }
}