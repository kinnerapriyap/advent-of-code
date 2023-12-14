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

fun <T> List<T>.findLongestSequence(): Pair<Int, Int> {
    val sequences = mutableListOf<Pair<Int, Int>>()
    for (startPos in indices) {
        for (sequenceLength in 1..(this.size - startPos) / 2) {
            var sequencesAreEqual = true
            for (i in 0 until sequenceLength)
                if (this[startPos + i] != this[startPos + sequenceLength + i]) {
                    sequencesAreEqual = false
                    break
                }
            if (sequencesAreEqual) sequences += Pair(startPos, sequenceLength)
        }
    }
    return sequences.maxBy { it.second }
}