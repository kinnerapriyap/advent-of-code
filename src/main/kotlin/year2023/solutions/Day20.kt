package year2023.solutions

import utils.data.lcm
import utils.setup.Day

class Day20 : Day(dayNumber = 20, year = 2023, useSampleInput = true) {

    private val hashMap = hashMapOf<String, Module>()

    sealed interface Module {
        val fromName: String
        val toNames: List<String>

        data class Broadcast(
            override val fromName: String,
            override val toNames: List<String>,
        ) : Module

        data class FlipFlop(
            override val fromName: String,
            override val toNames: List<String>,
        ) : Module

        data class Conjunction(
            val connected: List<String>,
            override val fromName: String,
            override val toNames: List<String>,
        ) : Module
    }

    private var input = inputList.map { str ->
        val (from, to) = str.split(" -> ")
        when (str[0]) {
            '%' -> Module.FlipFlop(
                fromName = from.substring(1),
                toNames = to.split(',').map { it.trim() }
            )

            '&' -> Module.Conjunction(
                connected = listOf(),
                fromName = from.substring(1),
                toNames = to.split(',').map { it.trim() }
            )

            else -> Module.Broadcast(
                fromName = from,
                toNames = to.split(',').map { it.trim() }
            )
        }
    }

    init {
        input = input.map {
            if (it is Module.Conjunction) {
                it.copy(
                    connected = input
                        .filter { m -> m.toNames.contains(it.fromName) }
                        .map { m -> m.fromName }
                )
            } else it
        }
        input.forEach {
            hashMap[it.fromName] = it
        }
    }

    override fun partOne(): Any {
        var (high, low) = 0 to 0
        val state = mutableMapOf<String, Boolean>()
        repeat(1000) {
            val q = mutableListOf<Pair<Boolean, String>>()
            q.add(false to "broadcaster")
            while (q.isNotEmpty()) {
                val (pulse, name) = q.removeFirst()
                if (pulse) high++ else low++
                hashMap[name]?.let { module ->
                    when (module) {
                        is Module.Broadcast -> {
                            module.toNames.forEach { to ->
                                q.add(pulse to to)
                            }
                        }

                        is Module.Conjunction -> {
                            val newPulse = !module.connected.all { state[it] ?: false }
                            state[name] = newPulse
                            module.toNames.forEach { to ->
                                q.add(newPulse to to)
                            }
                        }

                        is Module.FlipFlop -> {
                            if (!pulse) {
                                val newPulse = !(state[name] ?: false)
                                state[name] = newPulse
                                module.toNames.forEach { to ->
                                    q.add(newPulse to to)
                                }
                            }
                        }
                    }
                }
            }
        }
        return high * low
    }

    override fun partTwo(): Any {
        var count = 0
        val state = mutableMapOf<String, Boolean>()
        var (ks, kb) = 0 to 0
        var (jt, sx) = 0 to 0
        while (listOf(ks, kb, jt, sx).any { it == 0 }) {
            val q = mutableListOf<Pair<Boolean, String>>()
            q.add(false to "broadcaster")
            count++
            while (q.isNotEmpty()) {
                val (pulse, name) = q.removeFirst()
                hashMap[name]?.let { module ->
                    when (module) {
                        is Module.Broadcast -> {
                            module.toNames.forEach { to ->
                                q.add(pulse to to)
                            }
                        }

                        is Module.Conjunction -> {
                            val newPulse = !module.connected.all { state[it] ?: false }
                            state[name] = newPulse
                            module.toNames.forEach { to ->
                                q.add(newPulse to to)
                            }
                            if (newPulse)
                                when (name) {
                                    "ks" -> if (ks == 0) ks = count
                                    "kb" -> if (kb == 0) kb = count
                                    "jt" -> if (jt == 0) jt = count
                                    "sx" -> if (sx == 0) sx = count
                                }
                        }

                        is Module.FlipFlop -> {
                            if (!pulse) {
                                val newPulse = !(state[name] ?: false)
                                state[name] = newPulse
                                module.toNames.forEach { to ->
                                    q.add(newPulse to to)
                                }
                            }
                        }
                    }
                }
            }
            println("$ks $kb $jt $sx")
        }
        return ks.toLong().lcm(kb.toLong()).lcm(jt.toLong().lcm(sx.toLong()))
    }
}