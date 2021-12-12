fun main(args: Array<String>) {
    fun part1(input: List<String>): Int {
        val points = parseInput(input)

        return points
            .filter { (start, end) -> start.x == end.x || start.y == end.y }
            .getNumOverlaps()
    }

    fun part2(input: List<String>): Int {
        return parseInput(input).getNumOverlaps()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun parseInput(input: List<String>): List<Pair<Point, Point>> {
    val linePattern = "(\\d+),(\\d+).*?(\\d+),(\\d+)".toRegex()
    return input
        .mapNotNull { linePattern.matchEntire(it) }
        .map { it.groupValues.drop(1).map { it.toInt() } }
        .map { (x1, y1, x2, y2) -> Point(x1, y1) to Point(x2, y2) }
}

fun getLine(start: Point, end: Point): List<Point> {
    val xRange = if (end.x >= start.x) start.x..end.x else start.x downTo end.x
    val yRange = if (end.y >= start.y) start.y..end.y else start.y downTo end.y
    return mutableListOf<Point>().apply {
        val xit = xRange.iterator()
        val yit = yRange.iterator()
        var x = -1
        var y = -1
        while (xit.hasNext() || yit.hasNext()) {
            if (xit.hasNext()) x = xit.nextInt()
            if (yit.hasNext()) y = yit.nextInt()
            add(Point(x, y))
        }
    }
}

fun List<Pair<Point, Point>>.getNumOverlaps(): Int {
    return map { (start, end) -> getLine(start, end) }
        .flatten()
        .groupingBy { it }
        .eachCount()
        .filterValues { it > 1 }
        .size
}
