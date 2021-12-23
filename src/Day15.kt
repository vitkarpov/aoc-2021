fun main(args: Array<String>) {
    data class Point(val x: Int, val y: Int)
    fun bfs(m: List<List<Int>>): Int {
        val width = m.first().size
        val height = m.size

        val pq = mutableListOf<Pair<Point, Int>>(Point(0, 0) to 0)
        val visited = mutableListOf<Point>()
        val dirs = listOf(Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0))

        fun isBottomRightCorner(p: Point) = p.x == width - 1 && p.y == height - 1
        fun isWithinMap(p: Point) = 0 <= p.x && p.x < width && 0 <= p.y && p.y < height

        while (pq.size > 0) {
            pq.sortBy { it.second }
            val (cell, weight) = pq.removeAt(0)

            if (isBottomRightCorner(cell)) {
                return weight
            }
            dirs.forEach {
                val next = Point(cell.x + it.x, cell.y + it.y)
                if (isWithinMap(next) && !visited.contains(next)) {
                    visited.add(next)
                    pq.add(next to (weight + m[next.y][next.x]))
                }
            }
        }
        assert(false)
        return -1
    }
    fun part1(input: List<String>): Int {
        return bfs(input.map { it.toCharArray().map { it.toString().toInt() } })
    }
    fun part2(input: List<String>): Int {
        val m = input.map { it.toCharArray().map { it.toString().toInt() } }
        return MutableList(5 * m.size) { MutableList(5 * m.size) { 0 } }.apply {
            for (i in input.indices) {
                for (j in input.indices) {
                    for (k in 0..4) {
                        for (l in 0..4) {
                            this[i + k * m.size][j + l * m.size] = m[i][j] + k + l - if (m[i][j] + k + l > 9) 9 else 0
                        }
                    }
                }
            }
        }
            .let { bfs(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
