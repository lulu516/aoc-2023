class Day1 {
    private val numbersInWord = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    // 55538
    fun part1(): Long {
        return readLines(this)
            .map {
                it.toCharArray().partition { char -> char.isDigit() }.first
            }
            .map { it.first().toString() + it.last() }
            .sumOf { it.toLong() }
    }

    // 54875
    fun part2(): Int {
        return readLines(this)
            .map { getFirstDigit(it).toString() + getLastDigit(it) }
            .sumOf { it.toInt() }
    }

    private fun getFirstDigit(input: String): Char? {
        var visitedChars = ""
        input.toCharArray().forEach {
            visitedChars += it
            numbersInWord.forEach { word ->
                visitedChars = visitedChars.replace(word, (numbersInWord.indexOf(word) + 1).toString())
            }
            if (visitedChars.last().isDigit()) {
                return visitedChars.last()
            }
        }
        return null
    }

    private fun getLastDigit(input: String): Char? {
        var visitedChars = ""
        input.reversed().toCharArray().forEach {
            visitedChars = it + visitedChars
            numbersInWord.forEach { word ->
                visitedChars = visitedChars.replace(word, (numbersInWord.indexOf(word) + 1).toString())
            }
            if (visitedChars.first().isDigit()) {
                return visitedChars.first()
            }
        }
        return null
    }
}