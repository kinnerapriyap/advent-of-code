package utils.data

fun <T> List<List<T>>.get(row: Int, col: Int): T? =
    getOrNull(row)?.getOrNull(col)

fun <T> Array<Array<T>>.get(row: Int, col: Int): T? =
    getOrNull(row)?.getOrNull(col)

fun <T> List<List<T>>.get(point: Point): T? =
    getOrNull(point.row)?.getOrNull(point.col)

fun <T> Array<Array<T>>.get(point: Point): T? =
    getOrNull(point.row)?.getOrNull(point.col)

fun <T> List<List<T>>.getGridNeighbours(point: Point): List<T> =
    point.neighbors().mapNotNull { get(it) }

fun <T> Array<Array<T>>.getGridNeighbours(point: Point): List<T> =
    point.neighbors().mapNotNull { get(it) }

fun <T> List<List<T>>.has(thing: T): Boolean = any { any { it == thing } }

fun <T> Array<Array<T>>.has(thing: T): Boolean = any { any { it == thing } }

fun <T> List<List<T>>.hasAtIndex(thing: T): Point? {
    forEachIndexed { row, ts ->
        val col = ts.indexOf(thing)
        if (col != -1) return Point(row, col)
    }
    return null
}

fun <T> Array<Array<T>>.hasAtIndex(thing: T): Point? {
    forEachIndexed { row, ts ->
        val col = ts.indexOf(thing)
        if (col != -1) return Point(row, col)
    }
    return null
}

fun <T> List<List<T>>.printGrid() {
    println()
    println(joinToString("\n") { it.joinToString(" ") })
}

fun <T> Array<Array<T>>.printGrid() {
    println()
    println(joinToString("\n") { it.joinToString(" ") })
}
