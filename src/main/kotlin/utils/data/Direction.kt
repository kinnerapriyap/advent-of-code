package utils.data

enum class DirectionOrtho { UP, DOWN, RIGHT, LEFT }

fun verticalOrthoDirections() = listOf(DirectionOrtho.UP, DirectionOrtho.DOWN)

fun horizontalOrthoDirections() = listOf(DirectionOrtho.RIGHT, DirectionOrtho.LEFT)

fun DirectionOrtho.move(p: Point): Point =
    when (this) {
        DirectionOrtho.UP -> p.up()
        DirectionOrtho.DOWN -> p.down()
        DirectionOrtho.RIGHT -> p.right()
        DirectionOrtho.LEFT -> p.left()
    }