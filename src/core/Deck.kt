package core

import core.shuffeling.ShuffleEntry

class Deck(val Size: Int, val FaceWidth: Int, val SideWidth: Int) {

    val Cards : List<Card>
        get() = _cards


    private var _cards = MutableList(Size, {Card(FaceWidth, SideWidth)})

    fun Shuffle(shuffleEntries: List<ShuffleEntry>)
    {
        val shuffledCards = mutableListOf<Card>()

        for (shuffleEntry in shuffleEntries.sortedBy { it.DestinationIndex })
        {
            val card = _cards[shuffleEntry.SourceIndex]

            if(shuffleEntry.Flip)
                card.Flip()

            if (shuffleEntry.Turn)
                card.Turn()

            shuffledCards.add(shuffleEntry.DestinationIndex, card)
        }

        _cards = shuffledCards
    }

    fun getSideWidth(side: DeckSide) : Int
    {
        return when(side) {
            DeckSide.Left, DeckSide.Right -> SideWidth
            DeckSide.Front, DeckSide.Back -> FaceWidth
        }
    }
}