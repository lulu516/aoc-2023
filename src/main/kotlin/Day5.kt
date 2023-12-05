class Day5 {
    private val input = readLines(this)
    private val seeds = input.first().split(": ").last().split(" ").map { it.toLong() }
    private val seedsToSoil = translateMap(
        input.subList(
            input.indexOf("seed-to-soil map:") + 1,
            input.indexOf("soil-to-fertilizer map:") - 1
        )
    )
    private val soilToFertilizer = translateMap(
        input.subList(
            input.indexOf("soil-to-fertilizer map:") + 1,
            input.indexOf("fertilizer-to-water map:") - 1
        )
    )
    private val fertilizerToWater = translateMap(
        input.subList(
            input.indexOf("fertilizer-to-water map:") + 1,
            input.indexOf("water-to-light map:") - 1
        )
    )
    private val waterToLight = translateMap(
        input.subList(
            input.indexOf("water-to-light map:") + 1,
            input.indexOf("light-to-temperature map:") - 1
        )
    )
    private val lightToTemperature = translateMap(
        input.subList(
            input.indexOf("light-to-temperature map:") + 1,
            input.indexOf("temperature-to-humidity map:") - 1
        )
    )
    private val temperatureToHumidity = translateMap(
        input.subList(
            input.indexOf("temperature-to-humidity map:") + 1,
            input.indexOf("humidity-to-location map:") - 1
        )
    )
    private val humidityToLocation =
        translateMap(input.subList(input.indexOf("humidity-to-location map:") + 1, input.size - 1))

    // 289863851, 17ms
    fun part1(): Long {
        return seeds.asSequence()
            .map { seed -> seedsToSoil.getDestination(seed) }
            .map { soil -> soilToFertilizer.getDestination(soil) }
            .map { f -> fertilizerToWater.getDestination(f) }
            .map { w -> waterToLight.getDestination(w) }
            .map { l -> lightToTemperature.getDestination(l) }
            .map { t -> temperatureToHumidity.getDestination(t) }
            .map { h -> humidityToLocation.getDestination(h) }.toList()
            .min()
    }

    // 60568880, 297915ms (5min)
    fun part2(): Long {
        val seeds = input.first().split(": ").last().split(" ").map { it.toLong() }
            .chunked(2)
            .map { LongRange(it.first(), it.first() + it.last()) }

        var min = Long.MAX_VALUE

        seeds
            .parallelStream()
            .forEach {
                it.forEach { seed ->
                    val location = seedsToSoil.getDestination(seed)
                        .let { soil -> soilToFertilizer.getDestination(soil) }
                        .let { f -> fertilizerToWater.getDestination(f) }
                        .let { w -> waterToLight.getDestination(w) }
                        .let { l -> lightToTemperature.getDestination(l) }
                        .let { t -> temperatureToHumidity.getDestination(t) }
                        .let { h -> humidityToLocation.getDestination(h) }
                    min = minOf(min, location)
                }
            }

        return min
    }

    private fun translateMap(rangeList: List<String>): List<CategoryRange> {
        return rangeList.map { line ->
            val split = line.split(" ")
            val source = split[1].toLong()
            val destination = split.first().toLong()
            val range = split.last().toLong()
            CategoryRange(source, destination, range)
        }
    }

    private fun List<CategoryRange>.getDestination(input: Long): Long =
        this.firstNotNullOfOrNull { it.getDestination(input) } ?: input

    data class CategoryRange(val sourceStart: Long, val destinationStart: Long, val length: Long) {
        fun getDestination(input: Long): Long? {
            return if (input >= sourceStart && input < sourceStart + length) {
                destinationStart + (input - sourceStart)
            } else {
                null
            }
        }
    }
}