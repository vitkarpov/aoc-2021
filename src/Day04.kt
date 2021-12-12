data class Cell(
    val value: Int,
    var marked: Boolean = false
)

typealias Board = List<List<Cell>>

fun main(args: Array<String>) {
    fun part1(input: List<String>): Int {
        val nums = input.first().toNums()
        var boards = parseBoards(input)

        nums.forEach { num ->
            boards = takeSnapshot(boards, num)
            boards.findWinnerOrNull()?.let {
                return it.getScore() * num
            }
        }
        check(false)
        return -1
    }

    fun part2(input: List<String>): Int {
        val nums = input.first().toNums()
        var boards = parseBoards(input)

        nums.forEach { num ->
            boards = takeSnapshot(boards, num).let { next ->
                val m = next.toMutableList()
                next.findWinners().forEach {
                    if (m.size == 1) {
                        return it.getScore() * num
                    }
                    m.remove(it)
                }
                m.toList()
            }
        }
        check(false)
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun takeSnapshot(boards: List<Board>, num: Int): List<Board> {
    return boards.map { board ->
        board.map { row ->
            row.map { cell ->
                if (cell.value == num) {
                    Cell(cell.value, true)
                } else {
                    cell
                }
            }
        }
    }
}

fun Board.getScore(): Int {
    var score = 0
    this.forEach { row ->
        row.forEach { cell ->
            score += if (!cell.marked) cell.value else 0
        }
    }
    return score
}

fun parseBoards(input: List<String>): List<Board> {
    val boards = mutableListOf<MutableList<List<Cell>>>()
    input.drop(1).forEach {
        if (it.isEmpty()) {
            boards.add(mutableListOf<List<Cell>>())
        } else {
            boards.last().add(it.toBoardLine().map { Cell(it) })
        }
    }
    return boards.map { it.toList() }.toList()
}

fun List<Board>.findWinnerOrNull(): Board? {
    return this.find { isWinner(it) }
}

fun List<Board>.findWinners(): List<Board> {
    return this.filter { isWinner(it) }
}

fun isWinner(board: Board) =
    board.any { row -> row.all { it.marked } } ||
        board.transpose().any { col -> col.all { it.marked } }

fun Board.transpose(): Board {
    val rows = this.size
    val cols = this.first().size
    val result = List(cols) {
        (0 until rows).map { it to false }.toMutableList()
    }

    for (i in 0..rows - 1) {
        for (j in 0..cols - 1) {
            result[j][i] = this[i][j].value to this[i][j].marked
        }
    }
    return result.map { it.map { Cell(it.first, it.second) }.toList() }
}

fun String.toBoardLine(): List<Int> =
    this.split(" ").filter { it.isNotEmpty() }.map { it.toString().toInt() }

fun String.toNums(): List<Int> = this.split(",").map { it.toString().toInt() }
