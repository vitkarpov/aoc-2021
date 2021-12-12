fun main(args: Array<String>) {
    val m1 = mapOf(')' to '(', '}' to '{', ']' to '[', '>' to '<')
    val m2 = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')
    val scoreMap1 = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val scoreMap2 = mapOf(')' to 1L, ']' to 2L, '}' to 3L, '>' to 4L)

    fun score(s: String, st: MutableList<Char>): Int {
        st.add(s.first())
        for (ch in s.drop(1)) {
            if (ch == '(' || ch == '{' || ch == '[' || ch == '<') {
                st.add(ch)
            } else if (!st.isEmpty()) {
                val top = st.removeLast()
                if (top != m1[ch]) {
                    return scoreMap1[ch]!!
                }
            }
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        return input.map {
            val st = mutableListOf<Char>()
            score(it, st)
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val scores = input.map {
            val st = mutableListOf<Char>()
            val v = score(it, st)

            if (v > 0) {
                return@map null
            }
            st.toList().asReversed().map { m2[it] }.fold(0L) { acc, ch ->
                acc * 5L + scoreMap2[ch]!!
            }
        }.filterNotNull().sorted()

        return scores[scores.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
