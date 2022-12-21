package day16

import java.io.File

fun part1(lines: List<Valve>): Int {
    val canOpen = lines.count { it.flowRate > 0 }
    val startPath = Path(listOf(lines.find { it.name == "AA" } ?: error("No AA")), hashMapOf())
    var checkedPaths = listOf(startPath)
    var answer = startPath
    var time = 1
    while (time < 30) {
        val newPaths = arrayListOf<Path>()
        for (path in checkedPaths) {
            if (path.opened.size == canOpen) continue
            if (path.last.flowRate > 0 && !path.opened.containsKey(path.last))
                newPaths.add(
                    Path(
                        path.valves + path.last,
                        path.opened.toMutableMap().apply { set(path.last, 30 - time) }
                    )
                )
            val possible = path.last.tunnels.map { tunnel ->
                val valve = lines.find { it.name == tunnel } ?: error("no $tunnel")
                Path(path.valves + valve, path.opened)
            }
            newPaths.addAll(possible)
        }
        checkedPaths = newPaths.sortedByDescending { it.pressureReleased }.take(2000)
        if (checkedPaths.first().pressureReleased > answer.pressureReleased) answer = checkedPaths.first()
        time++
    }
    println(answer.valves.map { it.name })
    return answer.pressureReleased
}

fun part2(lines: List<Valve>): Int {
    return 0
}

fun main() {
    val lines = File("src/main/kotlin/day16/day16_input.txt")
        .bufferedReader()
        .readLines()
        .map { line ->
            val name = line.substringAfter("Valve ").split(" ")[0]
            val (flowRate, rest) = line.substringAfter("flow rate=").split(";")
            val rest2 = rest.substringAfter("valves ", "")
            val tunnels = if (rest2.isBlank()) listOf(rest.substringAfter("valve "))
            else rest2.split(", ")
            Valve(name, flowRate.toInt(), tunnels)
        }
    println(part1(lines))
    println(part2(lines))
}

data class Valve(val name: String, val flowRate: Int, val tunnels: List<String>)

data class Path(val valves: List<Valve>, val opened: Map<Valve, Int>) {
    val pressureReleased = opened.map { (valve, timeLeft) -> timeLeft * valve.flowRate }.sum()
    val last = valves.last()
}