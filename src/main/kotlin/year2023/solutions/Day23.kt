package year2023.solutions

import utils.data.DirectionOrtho
import utils.data.Point
import utils.data.get
import utils.data.move
import utils.setup.Day

class Day23 : Day(dayNumber = 23, year = 2023, useSampleInput = false) {
    sealed interface Trail {
        data object Path : Trail
        data object Forest : Trail
        data class Slope(val dir: DirectionOrtho) : Trail
    }

    private val input = inputList.mapIndexed { row, str ->
        str.mapIndexed { col, c ->
            when (c) {
                '#' -> Trail.Forest
                '>' -> Trail.Slope(DirectionOrtho.RIGHT)
                '<' -> Trail.Slope(DirectionOrtho.LEFT)
                '^' -> Trail.Slope(DirectionOrtho.UP)
                'v' -> Trail.Slope(DirectionOrtho.DOWN)
                else -> Trail.Path
            }
        }
    }

    private val start = Point(0, 1)
    private val end = Point(input.lastIndex, input[0].lastIndex - 1)

    override fun partOne(): Any {
        var max = 0
        fun re(curr: Point, visited: List<Point>) {
            if (curr == end) {
                max = maxOf(max, visited.size)
            }
            if (input.get(curr) is Trail.Slope) {
                (input.get(curr) as Trail.Slope).dir.move(curr)
                    .takeIf { input.get(it) != null && input.get(it) != Trail.Forest && it !in visited }
                    ?.let { re(it, visited + curr) }
            } else {
                curr.neighbors()
                    .filter { input.get(it) != null && input.get(it) != Trail.Forest && it !in visited }
                    .forEach { re(it, visited + curr) }
            }
        }
        re(start, listOf())
        return max
    }

    override fun partTwo(): Any {
        return dfsOptimized(adj(), start, end, mutableMapOf())!!
        val dp = Array(input.size) { Array(input[0].size) { 0 } }
        dp[0][1] = 0
        val q = mutableListOf<Pair<Point, List<Point>>>().apply {
            add(start to listOf())
        }
        while (q.isNotEmpty()) {
            val (curr, visited) = q.removeFirst()
            curr.neighbors()
                .filter { input.get(it) != null && input.get(it) != Trail.Forest && it !in visited }
                .forEach {
                    dp[it.row][it.col] = maxOf(dp[curr.row][curr.col] + 1, dp[it.row][it.col])
                    println("$it: ${dp[it.row][it.col]}")
                    q.add(it to visited + curr)
                }
        }
        return dp[input.lastIndex][input[0].lastIndex - 1]
    }

    private fun adj(): Map<Point, Map<Point, Int>> {
        val adj = input.indices.flatMap { x ->
            input[0].indices.mapNotNull { y ->
                if (input[x][y] != Trail.Forest) {
                    Point(x, y) to Point(x, y).neighbors().mapNotNull { (nx, ny) ->
                        if (nx in input.indices && ny in input[0].indices && input[nx][ny] != Trail.Forest)
                            Point(nx, ny) to 1
                        else null
                    }.toMap(mutableMapOf())
                } else null
            }
        }.toMap(mutableMapOf())

        adj.keys.toList().forEach { key ->
            adj[key]?.takeIf { it.size == 2 }?.let { neighbors ->
                val left = neighbors.keys.first()
                val right = neighbors.keys.last()
                val totalSteps = neighbors[left]!! + neighbors[right]!!
                adj.getOrPut(left) { mutableMapOf() }.merge(right, totalSteps, ::maxOf)
                adj.getOrPut(right) { mutableMapOf() }.merge(left, totalSteps, ::maxOf)
                listOf(left, right).forEach { adj[it]?.remove(key) }
                adj.remove(key)
            }
        }
        return adj
    }

    private fun dfsOptimized(
        graph: Map<Point, Map<Point, Int>>,
        cur: Point,
        end: Point,
        seen: MutableMap<Point, Int>
    ): Int? {
        if (cur == end) return seen.values.sum()
        var best: Int? = null
        (graph[cur] ?: emptyMap()).forEach { (neighbor, steps) ->
            if (neighbor !in seen) {
                seen[neighbor] = steps
                val res = dfsOptimized(graph, neighbor, end, seen)
                if (best == null || (res != null && res > best!!)) best = res
                seen.remove(neighbor)
            }
        }
        return best
    }
}