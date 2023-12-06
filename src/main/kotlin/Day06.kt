fun main() {

    fun retrieveTotalNumberOfWaysToWin(times: List<Long>, distances: List<Long>): Long {
        return times.mapIndexed { index, time ->
            val distance = distances[index]
            var numberOfWaysToWin = 0L
            for (seconds in 1 .. time) {
                if ((time - seconds) * seconds > distance) {
                    numberOfWaysToWin++
                }
            }
            numberOfWaysToWin
        }.reduce { acc, i -> acc * i }
    }

    fun part1(input: List<String>): Long {
        val times = NUMBER_REGEX.findAll(input[0]).map { it.groupValues[1].toLong() }.toList()
        val distances = NUMBER_REGEX.findAll(input[1]).map { it.groupValues[1].toLong() }.toList()
        return retrieveTotalNumberOfWaysToWin(times, distances)
    }

    fun part2(input: List<String>): Long {
        val time = input[0].filter { it.isDigit() }.toLong()
        val distance = input[1].filter { it.isDigit() }.toLong()
        return retrieveTotalNumberOfWaysToWin(listOf(time), listOf(distance))
    }

    val input = readInput("day6-input")
    part1(input).println()
    part2(input).println()
}
