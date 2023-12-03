package utils.data

data class Point(val row: Int, val col: Int) {
    override fun toString(): String = "($row,$col)"
    fun right() = Point(row + 1, col)
    fun left() = Point(row - 1, col)
    fun down() = Point(row, col + 1)
    fun up() = Point(row, col - 1)

    fun neighbors(): Set<Point> = setOf(right(), left(), down(), up())

    fun diagonals(): Set<Point> = setOf(
        Point(row + 1, col + 1),
        Point(row - 1, col - 1),
        Point(row + 1, col - 1),
        Point(row - 1, col + 1),
    )

    fun allSides(): Set<Point> = neighbors() + diagonals()
}