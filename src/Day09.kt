fun main(args: Array<String>) {
    fun searchMin(curr: Point, grid: Grid, cache: MutableMap<Point, Int>): Unit {
        val currValue = grid[curr.y][curr.x]
        if (
            grid[curr.y][curr.x - 1] > currValue &&
            grid[curr.y][curr.x + 1] > currValue &&
            grid[curr.y - 1][curr.x] > currValue &&
            grid[curr.y + 1][curr.x] > currValue
        ) {
            cache[curr] = currValue
        }
    }

    fun dfs(curr: Point, grid: MutableGrid): Int {
        val currValue = grid[curr.y][curr.x]
        if (currValue >= 9) {
            return 0
        }
        grid[curr.y][curr.x] = 9
        return 1 + (
            dfs(Point(curr.x, curr.y - 1), grid) +
            dfs(Point(curr.x, curr.y + 1), grid) +
            dfs(Point(curr.x - 1, curr.y), grid) +
            dfs(Point(curr.x + 1, curr.y), grid)
        )
    }

    fun parse(name: String) = readInput(name)
        .map { listOf(10) + it.map { it.toString().toInt() } + listOf(10) }
        .toMutableList().apply {
            val stubRow = (0 until first().size).map { 10 }.toMutableList()
            add(0, stubRow)
            add(stubRow)
        }
        .toList()

    fun part1(grid: Grid): Int {
        val cache = mutableMapOf<Point, Int>()
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, curr ->
                if (curr < 10) {
                    searchMin(Point(x, y), grid, cache)
                }
            }
        }
        return cache.values.map { it + 1 }.sum()
    }

    fun part2(grid: Grid): Int {
        val mutableGrid = grid.map { it.toMutableList() }.toMutableList()
        val all = mutableGrid.flatMapIndexed { y, row ->
            row.mapIndexed { x, curr ->
                if (curr < 10) dfs(Point(x, y), mutableGrid) else 0
            }
        }
        return all.sorted().takeLast(3).reduce { acc, curr -> acc * curr }
    }

    // test if implementation meets criteria from the description, like:
    val testInput: Grid = parse("Day09_test")

    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = parse("Day09")
    println(part1(input))
    println(part2(input))
}
