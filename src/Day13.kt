fun main(args: Array<String>) {
    data class Point(val x: Int, val y: Int)
    data class Instruction(val axis: Char, val line: Int)

    fun parseInput(name: String): Pair<Set<Point>, List<Instruction>> {
        val points = mutableSetOf<Point>()
        val instructions = mutableListOf<Instruction>()

        readInput(name).forEach {
            if (it.contains(',')) {
                val (x, y) = it.trim().split(',').map { it.toInt() }

                points.add(Point(x, y))
            } else if (it.contains("fold")) {
                val axis = it.split('=')[0].last()
                val line = it.split('=')[1].toInt()

                instructions.add(Instruction(axis, line))
            }
        }
        return points.toSet() to instructions.toList()
    }

    fun printGrid(points: Set<Point>) {
        val width = points.maxOf { it.x } + 1
        val height = points.maxOf { it.y } + 1
        val grid = List(height) { MutableList(width) { "." } }
        points.forEach { grid[it.y][it.x] = "#" }
        grid.forEach { line ->
            line.forEach { print(it) }
            println()
        }
    }

    fun part2(points: Set<Point>, instructions: List<Instruction>): Int {
        return points.toMutableSet().apply {
            instructions.forEach { (axis, line) ->
                val toRemove = mutableListOf<Point>()
                val toAdd = mutableListOf<Point>()
                forEach {
                    if (axis == 'x' && it.x > line) {
                        toRemove.add(it)
                        toAdd.add(Point(line - (it.x - line), it.y))
                    } else if (axis == 'y' && it.y > line) {
                        toRemove.add(it)
                        toAdd.add(Point(it.x, line - (it.y - line)))
                    }
                }
                removeAll(toRemove)
                addAll(toAdd)
            }
        }
            .let {
                // printGrid(it.toSet())
                it.size
            }
    }

    fun part1(points: Set<Point>, instructions: List<Instruction>): Int {
        return part2(points, listOf(instructions.first()))
    }

    // test if implementation meets criteria from the description, like:
    val (testPoints, testInstructions) = parseInput("Day13_test")
    check(part1(testPoints, testInstructions) == 17)
    check(part2(testPoints, testInstructions) == 16)

    val (points, instructions) = parseInput("Day13")
    println(part1(points, instructions))
    println(part2(points, instructions))
}
