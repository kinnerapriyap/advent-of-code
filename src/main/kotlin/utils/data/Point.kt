package utils.data

data class Point(val row: Int, val col: Int) {
    override fun toString(): String = "($row,$col)"
    fun right() = Point(row, col + 1)
    fun left() = Point(row, col - 1)
    fun down() = Point(row + 1, col)
    fun up() = Point(row - 1, col)

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

fun neighborsDelta(): Set<Point> = setOf(Point(0, 1), Point(1, 0), Point(0, -1), Point(-1, 0))

fun diagonalsDelta(): Set<Point> = setOf(Point(1, 1), Point(1, -1), Point(-1, -1), Point(-1, 1))