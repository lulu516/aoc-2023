import kotlin.time.measureTime

fun main() {
    val day = Day11()
    measureTime {
        println("part 1: " + day.part1())
    }.let {
        println("part 1 execution time: " + it.inWholeMilliseconds + "ms")
    }

    measureTime {
        println("part 2: " + day.part2())
    }.let {
        println("part 2 execution time: " + it.inWholeMilliseconds + "ms")
    }
}