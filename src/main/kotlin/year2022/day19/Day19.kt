package year2022.day19

import java.io.File
import kotlin.math.max

fun part1(lines: List<Blueprint>): Int = lines.sumOf { it.index * it.get(24) }

fun part2(lines: List<Blueprint>): Int =
    lines.take(3).fold(1) { acc, blueprint -> acc * blueprint.get(32) }

fun Blueprint.get(totalTime: Int): Int {
    val queue = ArrayDeque(listOf(State(0, Collection())))
    val seen = mutableSetOf<Collection>()
    val maxGeode = Array(totalTime + 1) { 0 to 0 }
    var geodes = 0
    while (queue.isNotEmpty()) {
        val (time, current) = queue.removeFirst()
        if (!seen.add(current)) continue
        if (time == totalTime) {
            geodes = max(geodes, current.geode)
            continue
        }
        if (current.canGetMaxGeodesInTimeLeft(totalTime - time, geodes)) continue
        if (current.isTooFarFromMax(time, maxGeode)) continue

        if (current.geodeRobot > maxGeode[time + 1].second)
            maxGeode[time + 1] = maxGeode[time + 1].first to current.geodeRobot
        if (current.geode > maxGeode[time + 1].first)
            maxGeode[time + 1] = current.geode to maxGeode[time + 1].second

        if (current.canBuildGeode(this)) {
            val new = current.deductCost(this, Type.GEODE)
                .incrementResources()
                .copy(geodeRobot = current.geodeRobot + 1)
            queue.addLast(State(time + 1, new))
        }
        if (current.canBuildObsidian(this)) {
            val new = current.deductCost(this, Type.OBSIDIAN)
                .incrementResources()
                .copy(obsidianRobot = current.obsidianRobot + 1)
            queue.addLast(State(time + 1, new))
        }
        if (current.canBuildOre(this)) {
            val new = current.deductCost(this, Type.ORE)
                .incrementResources()
                .copy(oreRobot = current.oreRobot + 1)
            queue.addLast(State(time + 1, new))
        }
        if (current.canBuildClay(this)) {
            val new = current.deductCost(this, Type.CLAY)
                .incrementResources()
                .copy(clayRobot = current.clayRobot + 1)
            queue.addLast(State(time + 1, new))
        }
        val new = current.incrementResources()
        queue.addLast(State(time + 1, new))
    }
    return geodes
}

fun main() {
    val lines = File("src/main/kotlin/year2022.day19/day19_input.txt")
        .bufferedReader()
        .readLines()
        .map { it.toBlueprint() }
    println(part1(lines))
    println(part2(lines))
}

fun Collection.isTooFarFromMax(time: Int, maxGeode: Array<Pair<Int, Int>>): Boolean =
    maxGeode[time + 1].second - geodeRobot > 1 || maxGeode[time + 1].first - geode > 1

fun Collection.canGetMaxGeodesInTimeLeft(timeLeft: Int, geodes: Int): Boolean =
    timeLeft * (timeLeft + geodeRobot) + geode < geodes

fun Collection.canBuildGeode(blueprint: Blueprint): Boolean =
    ore >= blueprint.geodeOreCost && obsidian >= blueprint.geodeObsidianCost

fun Collection.canBuildObsidian(blueprint: Blueprint): Boolean =
    ore >= blueprint.obsidianOreCost && clay >= blueprint.obsidianClayCost

fun Collection.canBuildOre(blueprint: Blueprint): Boolean = ore >= blueprint.oreCost

fun Collection.canBuildClay(blueprint: Blueprint): Boolean = ore >= blueprint.clayOreCost

enum class Type {
    ORE, CLAY, OBSIDIAN, GEODE
}

data class Blueprint(
    val index: Int,
    val oreCost: Int,
    val clayOreCost: Int,
    val obsidianOreCost: Int,
    val obsidianClayCost: Int,
    val geodeOreCost: Int,
    val geodeObsidianCost: Int
)

data class State(
    val time: Int,
    val collection: Collection
)

data class Collection(
    val ore: Int = 0,
    val oreRobot: Int = 1,
    val clay: Int = 0,
    val clayRobot: Int = 0,
    val obsidian: Int = 0,
    val obsidianRobot: Int = 0,
    val geode: Int = 0,
    val geodeRobot: Int = 0,
) {
    fun incrementResources(): Collection = copy(
        ore = ore + oreRobot,
        clay = clay + clayRobot,
        obsidian = obsidian + obsidianRobot,
        geode = geode + geodeRobot
    )

    fun deductCost(blueprint: Blueprint, type: Type): Collection =
        when (type) {
            Type.ORE -> copy(ore = ore - blueprint.oreCost)
            Type.CLAY -> copy(ore = ore - blueprint.clayOreCost)
            Type.OBSIDIAN -> copy(ore = ore - blueprint.obsidianOreCost, clay = clay - blueprint.obsidianClayCost)
            Type.GEODE -> copy(ore = ore - blueprint.geodeOreCost, obsidian = obsidian - blueprint.geodeObsidianCost)
        }
}

fun String.toBlueprint(): Blueprint {
    val index = substringAfter("Blueprint ").split(":")[0].toInt()
    val oreCost = substringAfter("Each ore robot costs ").split(" ")[0].toInt()
    val clayCost = substringAfter("Each clay robot costs ").split(" ")[0].toInt()
    val (obsidianOreCost, _, _, obsidianClayCost) =
        substringAfter("Each obsidian robot costs ").split(" ")
    val (geodeOreCost, _, _, geodeObsidianCost) =
        substringAfter("Each geode robot costs ").split(" ")
    return Blueprint(
        index = index,
        oreCost = oreCost,
        clayOreCost = clayCost,
        obsidianOreCost = obsidianOreCost.toInt(),
        obsidianClayCost = obsidianClayCost.toInt(),
        geodeOreCost = geodeOreCost.toInt(),
        geodeObsidianCost = geodeObsidianCost.toInt(),
    )
}
