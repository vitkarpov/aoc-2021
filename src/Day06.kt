fun main(args: Array<String>) {
    val dp = HashMap<Pair<Int, Int>, Long>()

    fun dfs(num: Int, days: Int): Long {
        return when {
            num == -1 -> dfs(6, days) + dfs(8, days)
            days == 0 -> 1L
            else -> dp[num to days] ?: dfs(num - 1, days - 1).also { dp[num to days] = it }
        }
    }

    fun solve(input: String, days: Int) =
        input.split(',').map { it.toInt() }.map { dfs(it, days) }.sum()

    fun part1(input: List<String>): Long {
        check(input.size == 1)
        return solve(input.first(), 80)
    }

    fun part2(input: List<String>): Long {
        check(input.size == 1)
        return solve(input.first(), 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
