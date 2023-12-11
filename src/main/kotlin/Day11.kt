import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day11 {
    // 9403026
    fun part1(): Long {
        val input = readLines(this)
        val multiplier = 1L

        return calculateDistance(input, multiplier)
    }

    // 543018317006
    fun part2(): Long {
        val input = readLines(this)
        val multiplier = 1000000L - 1

        return calculateDistance(input, multiplier)
    }

    private fun calculateDistance(input: List<String>, multiplier: Long): Long {
        val yToExpand = calculateRowsToExpand(input)
        val xToExpand = calculateAislesToExpand(input)

        val galaxies = getGalaxies(input)

        return getPairs(galaxies).sumOf { (a, b) ->
            val xToMultiple = xToExpand.filter { it in (min(a.x, b.x)..max(a.x, b.x)) }
            val yToMultiple = yToExpand.filter { it in (min(a.y, b.y)..max(a.y, b.y)) }
            abs(a.x - b.x) + abs(a.y - b.y) + xToMultiple.size * multiplier + yToMultiple.size * multiplier
        }
    }

    private fun getPairs(galaxies: List<Coordinate>) = galaxies.indices.flatMap { current ->
        (current + 1..<galaxies.size).map {
            Pair(galaxies[current], galaxies[it])
        }
    }

    private fun calculateAislesToExpand(input: List<String>) =
        (0..<input[0].length).filter { x -> !input.map { row -> row[x] }.contains('#') }

    private fun calculateRowsToExpand(input: List<String>) = input.indices.filter { !input[it].contains('#') }

    private fun getGalaxies(input: List<String>): List<Coordinate> {
        val galaxies = mutableListOf<Coordinate>()
        input.indices.forEach { y ->
            input[y].indices.forEach { x ->
                val current = Coordinate(x, y)
                if (input[y][x] == '#') {
                    galaxies.add(current)
                }
            }
        }
        return galaxies
    }
}