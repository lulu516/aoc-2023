class Day3 {
    private data class EngineNumber(val number: Int, val coordinates: List<Coordinate>)

    // 507214
    fun part1(): Int {
        val input = readLines(this)
        val yMax = input.size - 1
        val xMax = input.first().length - 1
        val (numbers, symbols) = parseInput(input)

        return numbers
            .filter { num -> getAdjacentSymbols(num, symbols, xMax, yMax).isNotEmpty() }
            .map { it.number }
            .sumOf { it }
    }

    // 72553319
    fun part2(): Long {
        val input = readLines(this)
        val yMax = input.size - 1
        val xMax = input.first().length - 1
        val (numbers, symbols) = parseInput(input)

        val gearCandidates = mutableMapOf<Coordinate, MutableList<Int>>()

        numbers.forEach { num ->
            getAdjacentSymbols(num, symbols, xMax, yMax)
                .forEach { symbol ->
                    gearCandidates.getOrPut(symbol) { mutableListOf() }.add(num.number)
                }
        }

        return gearCandidates.filter { it.value.size == 2 }.map { it.value.first() * it.value.last().toLong() }.sum()
    }

    private fun parseInput(input: List<String>): Pair<MutableList<EngineNumber>, MutableList<Coordinate>> {
        val numbers = mutableListOf<EngineNumber>()
        val symbols = mutableListOf<Coordinate>()

        input.indices.forEach { y ->
            val line = input[y]
            var numberXIndexes = mutableListOf<Int>()
            line.indices.forEach { x ->
                val char = line[x]
                if (char.isDigit()) {
                    numberXIndexes.add(x)
                } else {
                    if (numberXIndexes.isNotEmpty()) {
                        val number = line.substring(numberXIndexes.first(), numberXIndexes.last() + 1).toInt()
                        numbers.add(EngineNumber(number, numberXIndexes.map { Coordinate(it, y) }))
                        numberXIndexes = mutableListOf()
                    }
                    if (char != '.') {
                        symbols.add(Coordinate(x, y))
                    }
                }
            }
            if (numberXIndexes.isNotEmpty()) {
                val number = line.substring(numberXIndexes.first(), numberXIndexes.last() + 1).toInt()
                numbers.add(EngineNumber(number, numberXIndexes.map { Coordinate(it, y) }))
            }
        }
        return Pair(numbers, symbols)
    }

    private fun getAdjacentSymbols(
        num: EngineNumber,
        symbols: List<Coordinate>,
        xMax: Int,
        yMax: Int
    ): List<Coordinate> {
        return num.coordinates.flatMap { it.findAdjacentCoordinates(xMax, yMax, true) }
            .distinct()
            .filter { symbols.contains(it) }
    }
}