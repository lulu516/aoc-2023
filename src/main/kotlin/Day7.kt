class Day7 {
    // 248179786
    fun part1(): Int {
        val cardToBid = readLines(this).associate {
            val line = it.split(" ")
            line.first() to line.last().toInt()
        }
        val sortedCards = cardToBid.keys.sortedWith { c1, c2 ->
            val type1 = determineType(c1)
            val type2 = determineType(c2)
            if (type1 == type2) {
                compareSameType(c1, c2, "AKQJT98765432")
            } else {
                type1.rank - type2.rank
            }
        }
        return cardToBid.map { (card, bid) -> (sortedCards.indexOf(card) + 1) * bid }.sum()
    }

    // 247885995
    fun part2(): Int {
        val cardToBid = readLines(this).associate {
            val line = it.split(" ")
            line.first() to line.last().toInt()
        }
        val sortedCards = cardToBid.keys.sortedWith { c1, c2 ->
            val type1 = determineTypeWithWildCard(c1)
            val type2 = determineTypeWithWildCard(c2)
            if (type1 == type2) {
                compareSameType(c1, c2, "AKQT98765432J")
            } else {
                type1.rank - type2.rank

            }
        }
        return cardToBid.map { (card, bid) -> (sortedCards.indexOf(card) + 1) * bid }.sum()
    }

    private fun compareSameType(c1: String, c2: String, sort: String): Int {
        return if (c1.first() == c2.first()) {
            compareSameType(c1.drop(1), c2.drop(1), sort)
        } else {
            sort.indexOf(c2.first()) - sort.indexOf(c1.first())
        }

    }

    private fun determineType(cards: String): Type {
        val chars = cards.toCharArray().toList()
        if (chars.distinct().size == cards.length) {
            return Type.HIGH_CARD
        }

        if (chars.distinct().size == 1) {
            return Type.FIVE_OF_A_KIND
        }

        if (chars.distinct().size == 2) {
            val count = chars.count { it == chars.first() }
            return if (count == 1 || count == 4) Type.FOUR_OF_A_KIND else Type.FULL_HOUSE
        }

        if (chars.distinct().size == 3) {
            return if (chars.groupBy { it }.any { it.value.size == 3 }) Type.THREE_OF_A_KIND else Type.TWO_PAIR
        }

        return Type.ONE_PAIR
    }

    private fun determineTypeWithWildCard(cards: String): Type {
        val frequencies = cards.toCharArray().toList()
            .filter { it != 'J' }
            .groupingBy { it }
            .eachCount()

        if (frequencies.isEmpty()) {
            return Type.FIVE_OF_A_KIND
        }
        val mostCommonChar = frequencies.maxBy { it.value }.key
        return determineType(cards.replace('J', mostCommonChar))
    }

    /**
     * Five of a kind, where all five cards have the same label: AAAAA
     * Four of a kind, where four cards have the same label and one card has a different label: AA8AA
     * Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
     * Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
     * Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
     * One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
     * High card, where all cards' labels are distinct: 23456
     */
    private enum class Type(val rank: Int) {
        FIVE_OF_A_KIND(7), FOUR_OF_A_KIND(6), FULL_HOUSE(5), THREE_OF_A_KIND(4), TWO_PAIR(3), ONE_PAIR(2), HIGH_CARD(1)
    }
}