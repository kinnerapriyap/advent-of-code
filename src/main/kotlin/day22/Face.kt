package day22

data class Edge(
    val face: Face,
    val direction: Direction,
    val isUlta: Boolean
)

enum class Face(val startRow: Int, val startCol: Int) {
    FACE_1(0, inputSize),
    FACE_2(0, inputSize * 2),
    FACE_3(inputSize, inputSize),
    FACE_4(inputSize * 2, inputSize),
    FACE_5(inputSize * 2, 0),
    FACE_6(inputSize * 3, 0);

    companion object {
        fun Face.getEdges(): List<Edge> =
            when (this) {
                FACE_1 -> listOf(
                    Edge(FACE_6, Direction.RIGHT, false),
                    Edge(FACE_2, Direction.RIGHT, false),
                    Edge(FACE_3, Direction.DOWN, false),
                    Edge(FACE_5, Direction.RIGHT, true),
                )

                FACE_2 -> listOf(
                    Edge(FACE_6, Direction.UP, false),
                    Edge(FACE_4, Direction.LEFT, true),
                    Edge(FACE_3, Direction.LEFT, false),
                    Edge(FACE_1, Direction.LEFT, false),
                )

                FACE_3 -> listOf(
                    Edge(FACE_1, Direction.UP, false),
                    Edge(FACE_2, Direction.UP, false),
                    Edge(FACE_4, Direction.DOWN, false),
                    Edge(FACE_5, Direction.DOWN, false),
                )

                FACE_4 -> listOf(
                    Edge(FACE_3, Direction.UP, false),
                    Edge(FACE_2, Direction.LEFT, true),
                    Edge(FACE_6, Direction.LEFT, false),
                    Edge(FACE_5, Direction.LEFT, false),
                )

                FACE_5 -> listOf(
                    Edge(FACE_3, Direction.RIGHT, false),
                    Edge(FACE_4, Direction.RIGHT, false),
                    Edge(FACE_6, Direction.DOWN, false),
                    Edge(FACE_1, Direction.RIGHT, true),
                )

                FACE_6 -> listOf(
                    Edge(FACE_5, Direction.UP, false),
                    Edge(FACE_4, Direction.UP, false),
                    Edge(FACE_2, Direction.DOWN, false),
                    Edge(FACE_1, Direction.DOWN, false),
                )
            }
    }
}

fun findFace(row: Int, col: Int): Face =
    Face.values().find {
        row in it.startRow until it.startRow + inputSize &&
                col in it.startCol until it.startCol + inputSize
    }!!