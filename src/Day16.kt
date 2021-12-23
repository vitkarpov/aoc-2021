import kotlin.math.max
import kotlin.math.min

fun main(args: Array<String>) {
    var pointer = 0
    var versionSum = 0

    fun literal(packet: String): Long {
        var literal = packet.substring(pointer + 1, pointer + 5)
        if (packet[pointer] == '1') {
            do {
                pointer += 5
                literal += packet.substring(pointer + 1, pointer + 5)
            } while (packet[pointer] != '0')
        }
        pointer += 5
        return literal.toLong(2)
    }

    fun op(typeId: Int, first: Long?, second: Long): Long {
        if (first == null) {
            return second
        }
        return when (typeId) {
            0 -> first + second
            1 -> first * second
            2 -> min(first, second)
            3 -> max(first, second)
            5 -> if (first > second) 1 else 0
            6 -> if (second > first) 1 else 0
            7 -> if (first == second) 1 else 0
            else -> error("what")
        }
    }

    fun dfs(packet: String, depth: Int = 1): Long {
        versionSum += packet.take(3).toInt(2)
        val typeId = packet.substring(3, 6).toInt(2)
        pointer = 6
        return when (typeId) {
            4 -> literal(packet)
            else -> when (packet[pointer]) {
                '0' -> {
                    pointer++
                    val bitLength = packet.substring(pointer, pointer + 15).toInt(2)
                    pointer += 15
                    val end = pointer + bitLength
                    var temp = pointer
                    var value: Long? = null
                    while (temp < end) {
                        value = op(typeId, value, dfs(packet.substring(temp, end), depth + 1))
                        temp += pointer
                    }
                    pointer = end
                    value!!
                }
                '1' -> {
                    pointer++
                    val subPackets = packet.substring(pointer, pointer + 11).toInt(2)
                    pointer += 11
                    var start = pointer
                    var value: Long? = null
                    for (i in 1..subPackets) {
                        value = op(typeId, value, dfs(packet.substring(start, packet.lastIndex + 1), depth + 1))
                        start += pointer
                    }
                    pointer = start
                    value!!
                }
                else -> assert(false)
            } as Long
        }
    }

    val input = readInput("Day16")

    // part 2
    println(dfs(input.first().map { it.toString().toInt(16).toString(2).padStart(4, '0') }.joinToString("")))

    // part 1
    println(versionSum)
}
