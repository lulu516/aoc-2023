fun readLines(c: Any): List<String> = c.javaClass.getResourceAsStream(c.javaClass.name)!!.bufferedReader().readLines()

fun readLine(c: Any, separator: String): List<String> =
    c.javaClass.getResourceAsStream(c.javaClass.name)!!.bufferedReader().readLine().split(separator)

fun String.fromBinary() = Integer.parseInt(this, 2)

fun String.containsAll(str: String): Boolean = str.all { this.contains(it) }

fun String.isLowerCase() = this.lowercase() == this

fun String.toPair(): Pair<Char, Char> = Pair(this[0], this[1])

fun Int.sigma(): Int = if (this == 0) 0 else (1..this).sum()

fun List<Int>.concatToInt(): Int = Integer.valueOf(this.joinToString(""))

fun Long.findLCM(b: Long): Long {
    val larger = if (this > b) this else b
    val maxLcm = this * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % this == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}