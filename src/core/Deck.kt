package core

import core.shuffeling.Shuffle
import java.io.*

class Deck(val size: Int, private val faceWidth: Int, private val sideWidth: Int) : Serializable
{
    val Cards: List<Card>
        get() = _cards


    private var _cards = MutableList(size, { Card(faceWidth, sideWidth) })

    fun Shuffle(shuffle: Shuffle)
    {
        val shuffledCards = mutableListOf<Card>()

        for (shuffleEntry in shuffle.shuffleEntries.sortedBy { it.DestinationIndex })
        {
            val card = _cards[shuffleEntry.SourceIndex]

            if (shuffleEntry.Flip)
                card.Flip()

            if (shuffleEntry.Turn)
                card.Turn()

            shuffledCards.add(shuffleEntry.DestinationIndex, card)
        }

        _cards = shuffledCards
    }

    fun getSideWidth(side: DeckSide): Int
    {
        return when (side)
        {
            DeckSide.Left, DeckSide.Right -> sideWidth
            DeckSide.Front, DeckSide.Back -> faceWidth
        }
    }
}