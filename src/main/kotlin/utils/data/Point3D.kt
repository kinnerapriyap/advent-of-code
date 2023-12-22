package utils.data

data class Point3D(val x: Int, val y: Int, val z: Int) {
    override fun toString(): String = "($x,$y,$z)"
    fun getFaces() = setOf(
        Point3D(x + 1, y, z), Point3D(x - 1, y, z),
        Point3D(x, y + 1, z), Point3D(x, y - 1, z),
        Point3D(x, y, z + 1), Point3D(x, y, z - 1)
    )
}

// z, x, y
private fun List<List<List<Int>>>.x_y_z1() = get(1).map { it.toList() }.joinToString("\n")
private fun List<List<List<Int>>>.x_z_y0() =
    map { sq ->
        sq.indices.map { xI -> sq[xI].firstOrNull { it != 0 } ?: 0 }
    }.reversed().joinToString("\n")

private fun List<List<List<Int>>>.y_z_x0() =
    map { sq ->
        sq[0].indices.map { yI -> sq[sq.indices.firstOrNull { sq[it][yI] != 0 } ?: 0][yI] }
    }.reversed().joinToString("\n")