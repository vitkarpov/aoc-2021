fun main(args: Array<String>) {
    fun part1(input: List<Int>): Int {
        return input.zipWithNext().count { it.second > it.first }
    }

    fun part2(input: List<Int>): Int {
        var result = 0
        for (i in 3..input.size - 1) {
            if (input[i] > input[i - 3]) {
                result += 1
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
