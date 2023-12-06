private val mappingHeadingRegex = Regex("^(\\w+-to-\\w+) map:$")
private val numberRowRegex = Regex("^(\\d+)\\s(\\d+)\\s(\\d+)$")
private val seedsRegex = Regex("^seeds: [(\\d+)|\\s]+")

fun main() {

    var seedsString = ""
    var seedToSoilMappings = mutableListOf<Mapping>()
    var soilToFertilizerMappings = mutableListOf<Mapping>()
    var fertilizerToWaterMappings = mutableListOf<Mapping>()
    var waterToLightMappings = mutableListOf<Mapping>()
    var lightToTemperatureMappings = mutableListOf<Mapping>()
    var temperatureToHumidityMappings = mutableListOf<Mapping>()
    var humidityToLocationMappings = mutableListOf<Mapping>()

    var idToMappingsMap = mapOf(
        "seed-to-soil" to seedToSoilMappings,
        "soil-to-fertilizer" to soilToFertilizerMappings,
        "fertilizer-to-water" to fertilizerToWaterMappings,
        "water-to-light" to waterToLightMappings,
        "light-to-temperature" to lightToTemperatureMappings,
        "temperature-to-humidity" to temperatureToHumidityMappings,
        "humidity-to-location" to humidityToLocationMappings,
    )
    fun parseInput(input: List<String>) {
        var currentMappings: MutableList<Mapping>? = null
        input.forEach {line ->
            if (seedsRegex.matches(line)) {
                seedsString = line
            } else if (mappingHeadingRegex.matches(line)) {
                val mappingId = mappingHeadingRegex.find(line)?.groupValues?.get(1)
                currentMappings = idToMappingsMap[mappingId]!!
            } else if (numberRowRegex.matches(line)) {
                val matchResult = numberRowRegex.find(line)!!
                currentMappings?.add(Mapping(matchResult.groupValues[1].toLong(),matchResult.groupValues[2].toLong(),matchResult.groupValues[3].toLong()))
            }
        }
    }

    fun initialize(input: List<String>) {
        seedToSoilMappings = mutableListOf()
        soilToFertilizerMappings = mutableListOf()
        fertilizerToWaterMappings = mutableListOf()
        waterToLightMappings = mutableListOf()
        lightToTemperatureMappings = mutableListOf()
        temperatureToHumidityMappings = mutableListOf()
        humidityToLocationMappings = mutableListOf()

        idToMappingsMap = mapOf(
            "seed-to-soil" to seedToSoilMappings,
            "soil-to-fertilizer" to soilToFertilizerMappings,
            "fertilizer-to-water" to fertilizerToWaterMappings,
            "water-to-light" to waterToLightMappings,
            "light-to-temperature" to lightToTemperatureMappings,
            "temperature-to-humidity" to temperatureToHumidityMappings,
            "humidity-to-location" to humidityToLocationMappings,
        )
        parseInput(input)
    }

    fun retrieveDestinationForSource(source: Long, mappings: List<Mapping>) = mappings.firstNotNullOfOrNull { it.getDestination(source) } ?: source

    fun retrieveLocationForSeed(seed: Long): Long {
        val soil = retrieveDestinationForSource(seed, seedToSoilMappings)
        val fertilizer = retrieveDestinationForSource(soil, soilToFertilizerMappings)
        val water = retrieveDestinationForSource(fertilizer, fertilizerToWaterMappings)
        val light = retrieveDestinationForSource(water, waterToLightMappings)
        val temp = retrieveDestinationForSource(light, lightToTemperatureMappings)
        val humidity = retrieveDestinationForSource(temp, temperatureToHumidityMappings)
        return retrieveDestinationForSource(humidity, humidityToLocationMappings)
    }

    fun retrieveNearestLocation(seeds: List<Long>): Long {
        return seeds.map { seed ->
            retrieveLocationForSeed(seed)
        }.minOf { it }
    }

    fun part1(input: List<String>): Long {
        initialize(input)
        return retrieveNearestLocation(seedsString.substringAfter("seeds: ").split(" +".toRegex()).map {it.toLong()})
    }

    fun part2(input: List<String>): Long {
        initialize(input)
        return "(\\d+\\s\\d+)".toRegex().findAll(seedsString).map { matchResult ->
            val split = matchResult.groupValues[1].split(' ')
            LongRange(split[0].toLong(), split[0].toLong() + split[1].toLong() - 1)
        }.minOf { seedRange ->
            seedRange.minOf { retrieveLocationForSeed(it) }
        }
    }

    val input = readInput("day5-input")
    parseInput(input)
    part1(input).println()
    part2(input).println()
}

class Mapping(private val destinationRangeStart: Long, sourceRangeStart:Long, rangeLength: Long) {
    private val sourceRange = LongRange(sourceRangeStart, sourceRangeStart+rangeLength - 1)
    fun getDestination(source: Long): Long? {
        return if (sourceRange.contains(source)) {
            val position = source - sourceRange.first
            destinationRangeStart + position
        } else {
            null
        }
    }
}
