class Day8 {
    private val input = readLines(this)
    private val instructions = input.first()
    private val paths = input.drop(2).associate {
        val m = it.split(" = ")
        val key = m.first()
        val values = m.last().drop(1).dropLast(1).split(", ")
        key to Pair(values.first(), values.last())
    }

    // 21883
    fun part1(): Int {
        var count = 0
        var index = 0
        var current = "AAA"
        while (true) {
            count++
            val instruction = instructions[index]
            current = if (instruction == 'L') {
                paths[current]!!.first
            } else {
                paths[current]!!.second
            }
            if (current == "ZZZ") {
                return count
            }

            index = if (index == instructions.length - 1) 0 else index + 1
        }
    }

    // 12833235391111
    fun part2(): Long {
        return paths.keys.filter { it.last() == 'A' }
            .map { findNodeZ(it) }
            .reduce(Long::findLCM)
    }

    private fun findNodeZ(start: String): Long {
        var count = 0L
        var index = 0
        var current = start
        while (true) {
            count++
            val instruction = instructions[index]
            current = if (instruction == 'L') {
                paths[current]!!.first
            } else {
                paths[current]!!.second
            }
            if (current.last() == 'Z') {
                return count
            }

            index = if (index == instructions.length - 1) 0 else index + 1
        }
    }
}