class Day6 {
    // 128700
    fun part1(): Int {
        val input = readLines(this)
        val time = input.first().drop("Time:      ".length).split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val records = input.last().drop("Distance:  ".length).split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val timeToRecord = time.indices.map { time[it] to records[it] }
        return timeToRecord.map { (time, record) -> findWaysToWin(time, record) }
            .reduce(Int::times)
    }

    private fun findWaysToWin(time: Int, record: Int): Int {
        val count = time / 2 - (1..time / 2).first { it * (time - it) > record } + 1
        return if (time % 2 == 0) count * 2 - 1 else count * 2
    }

    // 39594072
    fun part2(): Long {
        val input = readLines(this)
        val time =
            input.first().drop("Time:      ".length).split(" ").filter { it.isNotEmpty() }.joinToString("").toLong()
        val record =
            input.last().drop("Distance:  ".length).split(" ").filter { it.isNotEmpty() }.joinToString("").toLong()
        val count = time / 2 - (1..time / 2).first { it * (time - it) > record } + 1
        return if (time % 2 == 0L) count * 2L - 1 else count * 2L
    }
}