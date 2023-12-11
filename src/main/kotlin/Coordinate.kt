data class Coordinate(val x: Int, val y: Int) {
    fun findAdjacentCoordinates(xMax: Int, yMax: Int, includeDiagonal: Boolean): List<Coordinate> {
        val left = if (x == 0) null else Coordinate(x - 1, y)
        val right = if (x == xMax) null else Coordinate(x + 1, y)
        val up = if (y == 0) null else Coordinate(x, y - 1)
        val down = if (y == yMax) null else Coordinate(x, y + 1)
        val leftUp = if (!includeDiagonal || (x == 0 || y == 0)) null else Coordinate(x - 1, y - 1)
        val leftDown =
            if (!includeDiagonal || (x == 0 || y == yMax)) null else Coordinate(x - 1, y + 1)
        val rightUp =
            if (!includeDiagonal || (x == xMax || y == 0)) null else Coordinate(x + 1, y - 1)
        val rightDown =
            if (!includeDiagonal || (x == xMax || y == yMax)) null else Coordinate(x + 1, y + 1)
        return listOfNotNull(
            left,
            right,
            up,
            down,
            leftUp,
            leftDown,
            rightUp,
            rightDown
        )
    }
}