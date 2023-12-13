package utils.data

fun Int.positiveMod(other: Int) =
    if (this % other < 0) other + (this % other)
    else this % other

fun Int.lcm(b: Int): Int = this * (b / gcd(b))

fun Long.lcm(b: Long): Long = this * (b / gcd(b))

fun Int.gcd(b: Int): Int {
    var (left, right) = this to b
    while (right > 0) {
        val temp = right
        right = left % right
        left = temp
    }
    return left
}

fun Long.gcd(b: Long): Long {
    var (left, right) = this to b
    while (right > 0) {
        val temp = right
        right = left % right
        left = temp
    }
    return left
}

infix fun <E> Collection<E>.symmetricDifference(other: Collection<E>): List<E> {
    val left = other.toMutableList()
    forEach { i -> left.remove(i) }
    val right = toMutableList()
    other.forEach { i -> right.remove(i) }
    return left + right
}