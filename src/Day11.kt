fun main(args: Array<String>) {
    data class Cell(var v: Int)

    fun dfs(grid: List<List<Cell>>, x: Int, y: Int) {
        if (grid[y][x].v < 10) {
            return
        }
        grid[y][x].v = -1

        val w = grid.first().size
        val h = grid.size

        listOf(
            (1 to 0), (-1 to 0), (0 to 1), (0 to -1),
            (1 to 1), (1 to -1), (-1 to 1), (-1 to -1)
        ).map { (dx, dy) ->
            val nx = x + dx
            val ny = y + dy

            if (0 <= nx && nx < w && 0 <= ny && ny < h) {
                nx to ny
            } else {
                null
            }
        }.filterNotNull().forEach { (nx, ny) ->
            if (grid[ny][nx].v != -1) {
                grid[ny][nx].v += 1
                dfs(grid, nx, ny)
            }
        }
    }

    fun next(grid: List<List<Cell>>): Int {
        var flashes = 0

        // 1) increment every single cell
        grid.forEach { it.forEach { it.v += 1 } }

        // 2) flash the ones that are ready
        grid.forEachIndexed { y, row -> row.forEachIndexed { x, _ -> dfs(grid, x, y) } }

        // 3) reset
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, _ ->
                if (grid[y][x].v == -1) {
                    grid[y][x].v = 0
                    flashes += 1
                }
            }
        }

        return flashes
    }

    fun part1(grid: Grid): Int {
        val cells = grid.map { it.map { Cell(it) } }
        return (0..99).map { next(cells) }.sum()
    }

    fun part2(grid: Grid): Int {
        val cells = grid.map { it.map { Cell(it) } }
        var steps = 0

        while (true) {
            steps += 1
            next(cells)
            if (cells.all { it.all { it.v == 0 } }) {
                return steps
            }
        }
    }

    fun parse(name: String): Grid = readInput(name).map { it.map { it - '0' } }

    // test if implementation meets criteria from the description, like:
    val testInput = parse("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = parse("Day11")
    println(part1(input))
    println(part2(input))
}
