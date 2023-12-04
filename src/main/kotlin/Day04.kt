import kotlin.math.pow

val numberRegex = Regex("(\\d+)")
fun main() {
    fun retrieveMatchingNumbersForCard(line: String): Set<Int> {
        val card = line.substringAfter(':').split('|')
        val winningNumbers = numberRegex.findAll(card[0]).map { matchResult -> matchResult.groupValues[1].toInt() }.toSet()
        val scratchCardNumbers = numberRegex.findAll(card[1]).map { matchResult -> matchResult.groupValues[1].toInt() }.toSet()
        return scratchCardNumbers intersect winningNumbers
    }
    fun calculateWinningTotalForCard(line: String): Int {
        val matches = retrieveMatchingNumbersForCard(line).size
        return if (matches == 0) {
            0
        } else {
            2F.pow(matches - 1).toInt()
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            calculateWinningTotalForCard(line)
        }
    }

    var cardsWon = 0
    // recursion hell - there is 100% a better solution than this
    fun addAnyWonCopies(input: List<String>, currentCard: String, currentIndex: Int) {
        val matchedNumbers = retrieveMatchingNumbersForCard(currentCard).size
        if (matchedNumbers > 0) {
            for (i in 1 .. matchedNumbers) {
                val indexToCopyAndCheck = currentIndex + i
                val copiedCard = input[indexToCopyAndCheck]
                cardsWon++
                addAnyWonCopies(input, copiedCard, indexToCopyAndCheck)
            }
        }
    }

    fun part2(input: List<String>): Int {
        input.forEachIndexed { index, card ->
            cardsWon++
            addAnyWonCopies(input, card, index)
        }
        return cardsWon
    }

    val input = readInput("day4-input")

    part1(input).println()
    part2(input).println()
}
