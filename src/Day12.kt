fun main(args: Array<String>) {
    fun getCaves(input: List<String>) =
        mutableMapOf<String, MutableList<String>>().apply {
            input.forEach {
                val parts = it.split("-")

                if (this.contains(parts[0])) {
                    this[parts[0]]!!.add(parts[1])
                } else {
                    this[parts[0]] = mutableListOf(parts[1])
                }
                if (this.contains(parts[1])) {
                    this[parts[1]]!!.add(parts[0])
                } else {
                    this[parts[1]] = mutableListOf(parts[0])
                }
            }
        }

    fun part1(input: List<String>): Int {
        val caves = getCaves(input)
        fun dfs(cave: String, path: List<String>): Int {
            return when {
                cave == "end" -> 1
                cave.all { it.isLowerCase() } && path.contains(cave) -> 0
                else -> caves[cave]?.sumBy { dfs(it, path + cave) } ?: 0
            }
        }
        return dfs("start", listOf())
    }

    fun part2(input: List<String>): Int {
        val caves = getCaves(input)
        fun dfs1(cave: String, path: List<String>): Int {
            return when {
                cave == "end" -> 1
                cave.all { it.isLowerCase() } && path.contains(cave) -> 0
                else -> caves[cave]?.sumOf { dfs1(it, path + cave) } ?: 0
            }
        }
        fun dfs2(cave: String, path: List<String>): Int {
            return when {
                cave == "end" -> 1
                cave == "start" && path.isNotEmpty() -> 0
                cave.all { it.isLowerCase() } && path.contains(cave) -> caves[cave]?.sumOf { dfs1(it, path + cave) } ?: 0
                else -> caves[cave]?.sumOf { dfs2(it, path + cave) } ?: 0
            }
        }
        return dfs2("start", listOf())
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
