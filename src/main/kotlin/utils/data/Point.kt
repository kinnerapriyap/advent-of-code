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
}