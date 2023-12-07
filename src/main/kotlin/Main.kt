import kotlin.time.measureTime

fun main() {
    measureTime {
        println("part 1: " + Day7().part1())
    }.let {
        println("part 1 execution time: " + it.inWholeMilliseconds + "ms")
    }

    measureTime {
        println("part 2: " + Day7().part2())
    }.let {
        println("part 2 execution time: " + it.inWholeMilliseconds + "ms")
    }
}