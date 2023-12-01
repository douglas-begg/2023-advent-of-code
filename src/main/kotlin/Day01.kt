val numberDict = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
    "1" to "1",
    "2" to "2",
    "3" to "3",
    "4" to "4",
    "5" to "5",
    "6" to "6",
    "7" to "7",
    "8" to "8",
    "9" to "9"
)

val reverseNumberDict = mapOf(
    "eno" to "1",
    "owt" to "2",
    "eerht" to "3",
    "ruof" to "4",
    "evif" to "5",
    "xis" to "6",
    "neves" to "7",
    "thgie" to "8",
    "enin" to "9",
    "1" to "1",
    "2" to "2",
    "3" to "3",
    "4" to "4",
    "5" to "5",
    "6" to "6",
    "7" to "7",
    "8" to "8",
    "9" to "9"
)

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line -> line.filter { it.isDigit() } }
            .sumOf { "${it.first()}${it.last()}".toInt() }
    }

    fun part2(input: List<String>): Int {
        val forwardPattern = Regex("(${numberDict.keys.joinToString("|")})")
        val reversePattern = Regex("(${reverseNumberDict.keys.joinToString("|")})")
        return input.map { line ->
            "${numberDict[forwardPattern.find(line)!!.value]}${reverseNumberDict[reversePattern.find(line.reversed())!!.value]}"
        }.sumOf { it.toInt() }
    }

    val input = readInput("day1-input")

    part1(input).println()
    part2(input).println()
}
