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

fun DirectionOrtho.left(): DirectionOrtho =
    when (this) {
        DirectionOrtho.UP -> DirectionOrtho.LEFT
        DirectionOrtho.DOWN -> DirectionOrtho.RIGHT
        DirectionOrtho.RIGHT -> DirectionOrtho.UP
        DirectionOrtho.LEFT -> DirectionOrtho.DOWN
    }

fun DirectionOrtho.right(): DirectionOrtho =
    when (this) {
        DirectionOrtho.UP -> DirectionOrtho.RIGHT
        DirectionOrtho.DOWN -> DirectionOrtho.LEFT
        DirectionOrtho.RIGHT -> DirectionOrtho.DOWN
        DirectionOrtho.LEFT -> DirectionOrtho.UP
    }

fun DirectionOrtho.opposite(): DirectionOrtho =
    when (this) {
        DirectionOrtho.UP -> DirectionOrtho.DOWN
        DirectionOrtho.DOWN -> DirectionOrtho.UP
        DirectionOrtho.RIGHT -> DirectionOrtho.LEFT
        DirectionOrtho.LEFT -> DirectionOrtho.RIGHT
    }