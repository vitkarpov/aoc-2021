fun main(args: Array<String>) {
    fun parseInput(input: List<String>): Map<String, Pair<String, String>> {
        return mutableMapOf<String, Pair<String, String>>().apply {
            input.drop(2).forEach {
                val (pair, v) = it.split(" -> ").map { it.trim() }
                set(pair, (pair[0].toString() + v) to (v + pair[1].toString()))
            }
        }
            .let { it.toMap() }
    }
    fun run(polymer: String, rules: Map<String, Pair<String, String>>, n: Int): Long {
        var pairs = mutableMapOf<String, Long>()
        val chars = mutableMapOf<Char, Long>()

        for (i in 0 until polymer.lastIndex) {
            val pair = polymer[i].toString() + polymer[i + 1]
            pairs[pair] = pairs[pair]?.plus(1) ?: 1L
            chars[polymer[i]] = chars[polymer[i]]?.plus(1) ?: 1L
        }
        chars[polymer.last()] = chars[polymer.last()]?.plus(1) ?: 1L

        for (i in 1..n) {
            mutableMapOf<String, Long>().apply {
                pairs.forEach { (pair, count) ->
                    set(rules[pair]!!.first, (get(rules[pair]!!.first) ?: 0) + count)
                    set(rules[pair]!!.second, (get(rules[pair]!!.second) ?: 0) + count)
                    chars[rules[pair]!!.first[1]] = (chars[rules[pair]!!.first[1]] ?: 0) + count
                }
                pairs = this
            }
        }

        return chars.values.maxOrNull()!! - chars.values.minOrNull()!!
    }

    fun part1(input: List<String>): Long {
        return run(input.first(), parseInput(input), 10)
    }
    fun part2(input: List<String>): Long {
        return run(input.first(), parseInput(input), 40)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588L)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
