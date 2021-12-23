import kotlin.math.max

fun main(args: Array<String>) {
    fun parseInput(name: String) = readInput(name).first().split(',').map { it.substringAfter("=").split("..").map { n -> n.toInt() } }

    fun part1(target: List<List<Int>>): Int {
        val maxY = -(target[1][0] + 1)

        return (maxY * (maxY + 1)) / 2
    }
    fun part2(target: List<List<Int>>): Int {
        val maxY = -(target[1][0] + 1)
        val xv = mutableListOf<Pair<Int, Int>>()
        val yv = mutableListOf<Pair<Int, Int>>()
        val maxSteps = (maxY * 2) + 2

        for (step in 1..maxSteps) {
            xv.addAll(
                (1..target[0][1]).mapNotNull { n ->
                    when {
                        (1..step).sumOf { max(0, n - (it - 1)) } in target[0][0]..target[0][1] -> step to n
                        else -> null
                    }
                }
            )
            yv.addAll(
                (target[1][0]..maxY).mapNotNull { n ->
                    when {
                        (1..step).sumOf { n - (it - 1) } in target[1][0]..target[1][1] -> step to n
                        else -> null
                    }
                }
            )
        }
        return yv.mapNotNull { y ->
            val x = xv.filter { it.first == y.first }
            when {
                x.isNotEmpty() -> x.map { Pair(it.second, y.second) }
                else -> null
            }
        }.flatten().toSet().size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = parseInput("Day17_test")
    check(part1(testInput) == 45)
    check(part2(testInput) == 112)

    val input = parseInput("Day17")
    println(part1(input))
    println(part2(input))
}
