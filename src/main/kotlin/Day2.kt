import kotlin.math.max
import kotlin.math.min

class Day2 {
    // 2162
    fun part1(): Int {
        val ids = mutableListOf<Int>()
        readLines(this).forEach { line ->
            val splits = line.split(": ")
            val id = splits.first().split(" ").last().toInt()
            val bags = splits.last().split("; ")
            if (bags.all { fitInBag(it) }) {
                ids.add(id)
            }
        }
        return ids.sum()
    }

    private fun fitInBag(cubesInString: String): Boolean {
        val cubes = cubesInString.split(", ")
        val colorToNumber = cubes.associate { cube ->
            val split = cube.split(" ")
            split.last() to split.first().toInt()
        }

        // 12 red cubes, 13 green cubes, and 14 blue
        return ((colorToNumber["red"] ?: 0) <= 12
                && (colorToNumber["green"] ?: 0) <= 13 &&
                (colorToNumber["blue"] ?: 0) <= 14)
    }

    // 72513
    fun part2(): Int {
        val result = readLines(this).map { line ->
            val splits = line.split(": ")
            val bags = splits.last().split("; ")
            val colorToNumber = mutableMapOf<String, Int>()
            bags.forEach {
                val cubes = it.split(", ")
                cubes.forEach { cube ->
                    val split = cube.split(" ")
                    colorToNumber[split.last()] =
                        max(colorToNumber.getOrDefault(split.last(), 0), split.first().toInt())
                }
            }
            colorToNumber.values.reduce(Int::times)
        }

        return result.sum()
    }
}