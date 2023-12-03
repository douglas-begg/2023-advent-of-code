import kotlin.math.max
import kotlin.math.min

val NUMBER_REGEX = Regex("(\\d+)")
val GEAR_REGEX = Regex("\\*")
const val RANGE_LIMIT = 139

fun main() {
    fun containsSymbolAtLocation(range: IntRange, line: String) =
        range.map { !line[it].isDigit() && line[it] != '.' }.reduce {acc, b -> acc || b}

    fun sumEnginePartsInLine(previousLine: String, currentLine: String, nextLine: String): Int {
        return NUMBER_REGEX.findAll(currentLine).map {
            val extendedRange = it.range.extendByOneInBothDirections(RANGE_LIMIT)
            if (containsSymbolAtLocation(extendedRange, previousLine) || containsSymbolAtLocation(extendedRange, currentLine) || containsSymbolAtLocation(extendedRange, nextLine)) {
                it.groupValues[1].toInt()
            } else {
                0
            }
        }.sum()
    }

    fun part1(input: List<String>): Int {
        return input.mapIndexed { i, line ->
            sumEnginePartsInLine(input[max(0, i-1)], line, input[min(139, i+1)])
        }.sum()
    }

    fun retrieveRatioNumbers(line: String?, gearIndex: Int): List<Int> {
        val ratioNumbers = mutableListOf<Int>()
        if (line != null) {
            NUMBER_REGEX.findAll(line).forEach { matchResult ->
                if (matchResult.range.extendByOneInBothDirections(RANGE_LIMIT).contains(gearIndex)) {
                    ratioNumbers.add(matchResult.groupValues[1].toInt())
                }
            }
        }
        return ratioNumbers
    }

    fun sumGearRatiosForGearsInLine(previousLine: String?, currentLine: String, nextLine: String?): Int {
        var sumOfGearRatios = 0
        val allMatches = GEAR_REGEX.findAll(currentLine).map { it.range.first }.toList()
        allMatches.forEach { gearIndex ->
            val ratioNumbers = mutableListOf<Int>()
            ratioNumbers += retrieveRatioNumbers(previousLine, gearIndex)
            ratioNumbers += retrieveRatioNumbers(currentLine, gearIndex)
            ratioNumbers += retrieveRatioNumbers(nextLine, gearIndex)

            if (ratioNumbers.size == 2) {
                sumOfGearRatios += ratioNumbers[0] * ratioNumbers[1]
            }
        }
        return sumOfGearRatios
    }

    fun part2(input: List<String>): Int {
        return input.mapIndexed { i, line ->
            val previousLine = if(i==0) { null } else { input[i-1] }
            val nextLine = if(i==139) { null } else { input[i+1] }
            sumGearRatiosForGearsInLine(previousLine, line, nextLine)
        }.sum()
    }

    val input = readInput("day3-input")

    part1(input).println()
    part2(input).println()
}
