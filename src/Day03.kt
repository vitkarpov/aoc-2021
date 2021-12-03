fun main(args: Array<String>) {
    fun part1(input: List<String>): Int {
        val gamma = mutableListOf<Int>();
        val height = input.first().length

        for (row in (0..height-1)) {
            var ones = 0;
            var zeroes = 0;
            for (item in input) {
                if (item[row] == '1') {
                    ones += 1;
                } else {
                    zeroes += 1;
                }
            }
            gamma.add(if (ones > zeroes) 1 else 0)
        }
        val alpha = gamma.map { if (it == 1) 0 else 1 }

        return gamma.joinToString("").toInt(2) * alpha.joinToString("").toInt(2)
    }

    fun part2(input: List<String>): Int {
        val oxy = input.toRating { ones, zeroes, bit -> if (ones >= zeroes) bit == '1' else bit == '0' }
        val co2 = input.toRating { ones, zeroes, bit -> if (zeroes <= ones) bit == '0' else bit == '1' }

        return oxy * co2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun List<String>.toRating(predicate: (Int, Int, Char) -> Boolean): Int {
    var result = this
    val len = this.first().length

    for (pos in (0..len - 1)) {
        var ones = 0;
        var zeroes = 0;
        
        for (item in result) {
            if (item[pos] == '1') {
                ones += 1;
            } else {
                zeroes += 1;
            }
        }
        result = result.filter { predicate(ones, zeroes, it[pos]) }
        if (result.size == 1) {
            return result.first().toInt(2)
        }
    }
    check(result.size == 1)
    return result.first().toInt(2)
}