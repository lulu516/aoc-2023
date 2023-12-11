data class BFS(val edges: List<Edge>) {
    data class Edge(val node1: Coordinate, val node2: Coordinate, val distance: Int = 1)

    fun findShortestPath(source: Coordinate, target: Coordinate): Result? {
        val dist = mutableMapOf<Coordinate, Int>()
        val prev = mutableMapOf<Coordinate, Coordinate?>()
        val toVisit = edges.flatMap { listOf(it.node1, it.node2) }.toMutableSet()

        toVisit.forEach { v ->
            dist[v] = Integer.MAX_VALUE
            prev[v] = null
        }
        dist[source] = 0

        while (toVisit.isNotEmpty()) {
            val current = toVisit.minByOrNull { dist[it] ?: 0 }
            toVisit.remove(current)

            if (current == target) {
                return Result(prev, dist, source, target)
            }
            edges
                .filter { it.node1 == current }
                .forEach { edge ->
                    val next = edge.node2
                    val alt = (dist[current] ?: 0) + edge.distance
                    if (alt < (dist[next] ?: 0)) {
                        dist[next] = alt
                        prev[next] = current
                    }
                }
        }

        return null
    }

    data class Result(
        val prev: Map<Coordinate, Coordinate?>,
        val dist: Map<Coordinate, Int>,
        val source: Coordinate,
        val target: Coordinate
    ) {
        fun getPath(): List<Coordinate> {
            val list: MutableList<Coordinate> = mutableListOf()
            var back = target
            while (back != source) {
                list.add(back)
                back = prev[back] ?: return list
            }

            list.add(source)
            return list
        }

        fun shortestDistance(): Int = dist[target]!!
    }
}