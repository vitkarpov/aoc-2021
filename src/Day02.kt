enum class Direction {
    forward, up, down
}

data class Entry(
    val dir: Direction,
    val units: Int
)

fun main(args: Array<String>) {
    fun part1(input: List<Entry>): Int {
        var length: Int = 0;
        var depth: Int = 0;

        for (entry in input) {
            when (entry.dir) {
                Direction.forward -> length += entry.units
                Direction.up -> depth -= entry.units
                Direction.down -> depth += entry.units
            }
            check(depth >= 0);
        }
        return depth * length
    }

    fun part2(input: List<Entry>): Long {
        var aim: Long = 0;
        var depth: Long = 0;
        var length: Long = 0;

        for (entry in input) {
            when (entry.dir) {
                Direction.forward -> {
                    length += entry.units
                    depth += aim * entry.units
                }
                Direction.up -> aim -= entry.units
                Direction.down -> aim += entry.units
            }
            check(depth >= 0);
        }
        return depth * length
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test").toEntry()
    check(part1(testInput) == 150)
    check(part2(testInput) == 900L)

    val input = readInput("Day02").toEntry()
    println(part1(input))
    println(part2(input))
}

fun List<String>.toEntry() = this.map {
    val parts = it.split(' ')
    Entry(Direction.valueOf(parts[0]), parts[1].toInt())
}