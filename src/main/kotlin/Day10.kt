class Day10 {
    private val valueByNode = mutableMapOf<Coordinate, Char>()
    private var loopPath: List<Coordinate> = emptyList()
    private var xMax = 0
    private var yMax = 0

    // 7005
    fun part1(): Int {
        val input = readLines(this)
        yMax = input.size - 1
        xMax = input.first().length - 1
        var start: Coordinate? = null
        input.indices.forEach { y ->
            input[y].indices.forEach { x ->
                val value = input[y][x]
                val coordinate = Coordinate(x, y)
                if (value != '.') {
                    valueByNode[coordinate] = value
                    if (value == 'S') {
                        start = coordinate
                    }
                }
            }
        }

        val edges = mutableListOf<BFS.Edge>()
        valueByNode.forEach { (coordinate, value) ->
            getNext(coordinate, value).forEach { next -> edges.add(BFS.Edge(coordinate, next)) }
        }
        val loop = getNext(start!!, 'S')
            .filter { next -> valueByNode.containsKey(next) }
            .firstNotNullOf { next ->
                val refinedEdges = edges.filterNot { it.node2 == start && it.node1 == next }
                val bfs = BFS(refinedEdges)
                bfs.findShortestPath(next, start!!)
            }

        loopPath = loop.getPath()
        return (loop.shortestDistance() + 1) / 2
    }

    // 417
    fun part2(): Int {
        var count = 0
        (0..yMax).forEach { y ->
            var inLoop = false
            (0..xMax).forEach { x ->
                val current = Coordinate(x, y)
                if (loopPath.contains(current)) {
                    val value = valueByNode[current]
                    if (value in listOf('|', 'J', 'L')) {
                        inLoop = !inLoop
                    }
                } else {
                    if (inLoop) {
                        count++
                    }
                }
            }
        }

        return count
    }

    private fun getNext(coordinate: Coordinate, value: Char): List<Coordinate> {
        return when (value) {
            'S' -> listOfNotNull(
                getLeft(coordinate),
                getRight(coordinate),
                getUp(coordinate),
                getDown(coordinate)
            )

            '7' -> listOfNotNull(getLeft(coordinate), getDown(coordinate))
            'L' -> listOfNotNull(getRight(coordinate), getUp(coordinate))
            'J' -> listOfNotNull(getLeft(coordinate), getUp(coordinate))
            'F' -> listOfNotNull(getRight(coordinate), getDown(coordinate))
            '|' -> listOfNotNull(getUp(coordinate), getDown(coordinate))
            '-' -> listOfNotNull(getLeft(coordinate), getRight(coordinate))
            else -> listOf()
        }
    }

    private fun getDown(coordinate: Coordinate): Coordinate? {
        return Coordinate(coordinate.x, coordinate.y + 1)
            .takeIf {
                valueByNode[it]?.let { char -> char in listOf('S', 'J', 'L', '|') } ?: false
            }
    }

    private fun getRight(coordinate: Coordinate): Coordinate? {
        return Coordinate(coordinate.x + 1, coordinate.y)
            .takeIf {
                valueByNode[it]?.let { char -> char in listOf('S', '7', 'J', '-') } ?: false
            }
    }

    private fun getLeft(coordinate: Coordinate): Coordinate? {
        return Coordinate(coordinate.x - 1, coordinate.y)
            .takeIf {
                valueByNode[it]?.let { char -> char in listOf('S', 'L', 'F', '-') } ?: false
            }
    }

    private fun getUp(coordinate: Coordinate): Coordinate? {
        return Coordinate(coordinate.x, coordinate.y - 1)
            .takeIf {
                valueByNode[it]?.let { char -> char in listOf('S', '7', 'F', '|') } ?: false
            }
    }
}