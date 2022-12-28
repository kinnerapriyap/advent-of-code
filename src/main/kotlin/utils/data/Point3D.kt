package utils.data

data class Point3D(val x: Int, val y: Int, val z: Int) {
    override fun toString(): String = "($x,$y,$z)"
    fun getFaces() = setOf(
        Point3D(x + 1, y, z), Point3D(x - 1, y, z),
        Point3D(x, y + 1, z), Point3D(x, y - 1, z),
        Point3D(x, y, z + 1), Point3D(x, y, z - 1)
    )
}