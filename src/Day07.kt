import kotlin.math.*

fun main(args: Array<String>) {
    fun part1(input: List<String>): Int {
        check(input.size == 1)
        val nums = input.first().split(",").map { it.toInt() }
        val sorted = nums.sorted()
        return nums.map { abs(it - sorted[sorted.size / 2]) }.sum()
    }

    fun part2(input: List<String>): Int {
        check(input.size == 1)
        val nums = input.first().split(",").map { it.toInt() }
        val mean = nums.average()
        val toFloor = floor(mean).toInt()
        val toCeil = ceil(mean).toInt()

        return min(nums.map { getCost(abs(it - toCeil)) }.sum(), nums.map { getCost(abs(it - toFloor)) }.sum())
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

fun getCost(n: Int) = n * (n + 1) / 2