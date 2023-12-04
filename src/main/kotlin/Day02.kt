
const val MAX_AVAILABLE_RED = 12
const val MAX_AVAILABLE_GREEN = 13
const val MAX_AVAILABLE_BLUE = 14

val GREEN_REGEX = Regex("(\\d+) green")
val BLUE_REGEX = Regex("(\\d+) blue")
val RED_REGEX = Regex("(\\d+) red")

fun main() {
    fun part1(input: List<String>): Int {
        return input.mapIndexed { i, line ->
            val maxGreen = GREEN_REGEX.findAll(line).map { it.groupValues[1].toInt() }.max()
            val maxBlue = BLUE_REGEX.findAll(line).map { it.groupValues[1].toInt() }.max()
            val maxRed = RED_REGEX.findAll(line).map { it.groupValues[1].toInt() }.max()
            if (maxGreen <= MAX_AVAILABLE_GREEN && maxBlue <= MAX_AVAILABLE_BLUE && maxRed <= MAX_AVAILABLE_RED) {
                i + 1
            } else {
                0
            }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val maxGreen = GREEN_REGEX.findAll(line).map { it.groupValues[1].toInt() }.max()
            val maxBlue = BLUE_REGEX.findAll(line).map { it.groupValues[1].toInt() }.max()
            val maxRed = RED_REGEX.findAll(line).map { it.groupValues[1].toInt() }.max()
            maxGreen * maxBlue * maxRed
        }.sum()
    }

    val input = readInput("day2-input")

    part1(input).println()
    part2(input).println()
}
