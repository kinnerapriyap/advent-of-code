package utils.data

enum class DirectionOrtho { UP, DOWN, RIGHT, LEFT }

fun DirectionOrtho.toDelta(): Point =
    when (this) {
        DirectionOrtho.UP -> upDelta
        DirectionOrtho.DOWN -> downDelta
        DirectionOrtho.RIGHT -> rightDelta
        DirectionOrtho.LEFT -> leftDelta
    }

fun Point.toDirectionOrtho(): DirectionOrtho =
    when (this) {
        upDelta -> DirectionOrtho.UP
        downDelta -> DirectionOrtho.DOWN
        rightDelta -> DirectionOrtho.RIGHT
        leftDelta -> DirectionOrtho.LEFT
        else -> DirectionOrtho.UP
    }

fun DirectionOrtho.toSign(): Char =
    when (this) {
        DirectionOrtho.UP -> '^'
        DirectionOrtho.DOWN -> 'v'
        DirectionOrtho.RIGHT -> '>'
        DirectionOrtho.LEFT -> '<'
    }

val directionOrthoSigns = listOf('^', 'v', '>', '<')

fun Char.toDirectionOrtho(): DirectionOrtho =
    when (this) {
        '^' -> DirectionOrtho.UP
        'v' -> DirectionOrtho.DOWN
        '>' -> DirectionOrtho.RIGHT
        '<' -> DirectionOrtho.LEFT
        else -> DirectionOrtho.UP
    }

fun verticalOrthoDirections() = listOf(DirectionOrtho.UP, DirectionOrtho.DOWN)

fun horizontalOrthoDirections() = listOf(DirectionOrtho.RIGHT, DirectionOrtho.LEFT)

fun DirectionOrtho.move(p: Point): Point =
    when (this) {
        DirectionOrtho.UP -> p.up()
        DirectionOrtho.DOWN -> p.down()
        DirectionOrtho.RIGHT -> p.right()
        DirectionOrtho.LEFT -> p.left()
    }

fun Point.move(dir: DirectionOrtho): Point =
    when (dir) {
        DirectionOrtho.UP -> up()
        DirectionOrtho.DOWN -> down()
        DirectionOrtho.RIGHT -> right()
        DirectionOrtho.LEFT -> left()
    }

fun DirectionOrtho.move(p: Point, steps: Int): Point {
    var o = p
    repeat(steps) { o = move(o) }
    return o
}

fun Point.move(dir: DirectionOrtho, steps: Int): Point {
    var o = this
    repeat(steps) { o = move(dir) }
    return o
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