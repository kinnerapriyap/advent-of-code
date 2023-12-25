package year2023.solutions

import org.jgrapht.alg.StoerWagnerMinimumCut
import org.jgrapht.graph.DefaultWeightedEdge
import org.jgrapht.graph.SimpleWeightedGraph
import utils.setup.Day

class Day25 : Day(dayNumber = 25, year = 2023, useSampleInput = false) {
    override fun partOne(): Any {
        val graph = SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)
        inputList.forEach { line ->
            val (name, others) = line.split(": ")
            graph.addVertex(name)
            others.split(" ").forEach { other ->
                graph.addVertex(other)
                graph.addEdge(name, other)
            }
        }
        val oneSideSize = StoerWagnerMinimumCut(graph).minCut().size
        return (graph.vertexSet().size - oneSideSize) * oneSideSize
    }
}