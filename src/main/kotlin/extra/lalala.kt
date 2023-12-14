package extra

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun insertGreatestCommonDivisors(head: ListNode?): ListNode? {
    var after = head
    while (after?.next != null) {
        insertBetweenNodes(after, after.next!!)
        after = after.next?.next
    }
    return head
}

private fun insertBetweenNodes(first: ListNode, second: ListNode) =
    ListNode(findGCD(first.`val`, second.`val`)).apply {
        first.next = this
        this.next = second
    }

private fun findGCD(one: Int, two: Int): Int {
    val (big, small) = if (one > two) one to two else two to one
    val modulo = big % small
    if (modulo == 0) return small
    return findGCD(modulo, small)
}

private fun findGCD2(one: Int, two: Int) : Int {
    var (biggie, smallie) = if (one > two) one to two else two to one
    var modulo = biggie % smallie
    while (modulo != 0) {
        val (big, small) = if (modulo > smallie) modulo to smallie else smallie to modulo
        smallie = small
        modulo = big % small
    }
    return smallie
}

fun findGCD3(a: Long, b: Long): Long {
    if (b == 0L) return a
    var small = a
    var bigger = b
    while (small != 0L) {
        val temp = bigger
        bigger = small
        small = temp % small
    }
    return bigger
}

private fun findGCD4(a: Int, b: Int): Int =
    if (a == 0) b else findGCD4(b % a, a)
