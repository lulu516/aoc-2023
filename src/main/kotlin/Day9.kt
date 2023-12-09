class Day9 {
    // 1479011877
    fun part1(): Int {
        return readLines(this)
            .map { it.split(" ").map { str -> str.toInt() } }.sumOf { getNextValue(it) }
    }

    // 973
    fun part2(): Int {
        return readLines(this)
            .map { it.split(" ").map { str -> str.toInt() } }
            .sumOf { getFirstValue(it) }
    }

    private fun getNextValue(history: List<Int>): Int {
        val allHistory = getAllHistory(history)
        return allHistory.sumOf { it.last() }
    }

    private fun getFirstValue(history: List<Int>): Int {
        val allHistory = getAllHistory(history)
        val allFirst = allHistory.map { it.first() }
        return allFirst.indices.sumOf { if (it % 2 == 0) allFirst[it] else -allFirst[it] }
    }


    /**
     * 10  13  16  21  30  45  68
     *    3   3   5   9  15  23
     *      0   2   4   6   8
     *        2   2   2   2
     *          0   0   0
     */
    private fun getAllHistory(history: List<Int>): List<List<Int>> {
        val allHistory = mutableListOf(history)
        var previousHistory = history
        while (previousHistory.any { it != 0 }) {
            previousHistory = getPreviousHistory(previousHistory)
            allHistory.add(previousHistory)
        }
        return allHistory
    }

    private fun getPreviousHistory(history: List<Int>): List<Int> {
        return (1..<history.size).map {
            history[it] - history[it - 1]
        }
    }
}