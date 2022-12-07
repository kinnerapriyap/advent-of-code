package day07

import java.io.File

class Node(
    val value: String,
    var children: MutableList<Node> = mutableListOf(),
    var parent: Node? = null,
    var size: Int = 0,
    val isDir: Boolean
) {
    override fun toString(): String = StringBuilder("$value $size").apply {
        if (children.isNotEmpty()) append(" {" + children.map { it.toString() } + " }")
    }.toString()
}

fun part1(root: Node): Int {
    var sum = 0
    if (root.isDir) {
        if (root.size < 100000) sum += root.size
        root.children.forEach { sum += part1(it) }
    }
    return sum
}


fun part2(root: Node): Int = traverseDir(root).sorted().find { it > root.size - 40000000 } ?: 0

fun traverseDir(node: Node?): List<Int> =
    if (node == null) emptyList()
    else mutableListOf<Int>().apply {
        if (node.isDir) add(node.size)
        node.children.forEach { addAll(traverseDir(it)) }
    }

fun addAllNodes(lines: List<String>): Node {
    val root = Node("/", isDir = true)
    var current: Node? = root
    lines.forEach { line ->
        when {
            line.startsWith("$ ls") -> {}
            line.startsWith("$ cd ..") -> current = current?.parent
            line.startsWith("$ cd /") -> current = root
            line.startsWith("$ cd") ->
                current = current?.children?.find { it.value == line.substring(5) && it.isDir }

            line.startsWith("dir") ->
                current?.children?.add(Node(line.substring(4), parent = current, isDir = true))

            else -> {
                val (size, name) = line.split(" ", limit = 2)
                val new = Node(name, parent = current, size = size.toInt(), isDir = false)
                if (current?.children?.any { it.value == name } == false) {
                    current?.children?.add(new)
                    current?.size = (current?.size ?: 0) + size.toInt()
                    var parent = current?.parent
                    while (parent != null) {
                        parent.size = parent.size + size.toInt()
                        parent = parent.parent
                    }
                }
            }
        }
    }
    return root
}

fun main() {
    val lines = File("src/main/kotlin/day07/day7_input.txt")
        .bufferedReader()
        .readLines()
    val root = addAllNodes(lines)
    println(root.toString())
    println(part1(root))
    println(part2(root))
}