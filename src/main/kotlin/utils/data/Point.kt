package utils.data

data class Point(val row: Int, val col: Int) {
    override fun toString(): String = "($row,$col)"
    fun right() = add(rightDelta)
    fun left() = add(leftDelta)
    fun down() = add(downDelta)
    fun up() = add(upDelta)

    fun neighbors(): Set<Point> = setOf(right(), left(), down(), up())

    fun diagonals(): Set<Point> = setOf(
        Point(row + 1, col + 1),
        Point(row - 1, col - 1),
        Point(row + 1, col - 1),
        Point(row - 1, col + 1),
    )

    fun allSides(): Set<Point> = neighbors() + diagonals()

    fun add(point: Point): Point = Point(row + point.row, col + point.col)
}

val rightDelta = Point(0, 1)
val leftDelta = Point(0, -1)
val upDelta = Point(-1, 0)
val downDelta = Point(1, 0)
val upRightDelta = Point(1, 1)
val upLeftDelta = Point(1, -1)
val downRightDelta = Point(-1, 1)
val downLeftDelta = Point(-1, -1)

val neighborsDelta: Set<Point> = setOf(rightDelta, leftDelta, upDelta, downDelta)
val diagonalsDelta: Set<Point> = setOf(upRightDelta, upLeftDelta, downRightDelta, downLeftDelta)
val allDelta = neighborsDelta + diagonalsDelta