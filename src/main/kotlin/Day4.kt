import kotlin.math.min
import kotlin.math.pow

class Day4 {
    // 28538
    fun part1(): Int {
        return readLines(this)
            .map { parseToCard(it) }
            .map {
                val wonNumbers = it.findWonNumbers()
                if (wonNumbers.isEmpty())
                    0
                else
                    2.toDouble().pow((wonNumbers.size - 1).toDouble()).toInt()
            }
            .sumOf { it }
    }

    // 9425061
    fun part2(): Int {
        val originalCards = readLines(this).map { parseToCard(it) }.associateBy { it.nr }
        val totalCards = originalCards.values.toMutableList()
        originalCards.forEach {
            getCopies(it.value, originalCards, totalCards)
        }

        return totalCards.size
    }

    private fun parseToCard(it: String): Card {
        val nrToCards = it.split(": ")
        val winningToOwnNumbers = nrToCards.last().split(" | ")
        val winningNumbers =
            winningToOwnNumbers.first().split(" ").filter { str -> str.isNotEmpty() }.map { num -> num.toInt() }
        val ownNumbers =
            winningToOwnNumbers.last().split(" ").filter { str -> str.isNotEmpty() }.map { num -> num.toInt() }
        return Card(nrToCards.first().split(" ").last().toInt(), winningNumbers, ownNumbers)
    }

    private fun getCopies(currentCard: Card, original: Map<Int, Card>, totalCards: MutableList<Card>) {
        val copies = currentCard.findWonNumbers().size
        if (copies > 0 && currentCard.nr < original.size) {
            (currentCard.nr + 1..min(original.size, currentCard.nr + copies))
                .forEach {
                    original[it]?.let { copy ->
                        totalCards.add(copy)
                        getCopies(copy, original, totalCards)
                    }
                }
        }
    }

    data class Card(val nr: Int, val winning: List<Int>, val own: List<Int>) {
        fun findWonNumbers(): List<Int> {
            return own.filter { winning.contains(it) }
        }
    }
}