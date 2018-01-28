package core

import core.shuffeling.CustomShuffle
import core.shuffeling.ShuffleEntry
import core.shuffeling.SimpleShuffleType
import org.junit.Assert.assertEquals
import org.junit.Test

class DeckTests
{
    @Test
    fun testShuffle()
    {
        val deck = Deck(2, 5, 10)


        val shuffleEntries = listOf(ShuffleEntry(0, 1, false, true), ShuffleEntry(1, 0, true, false))
        val shuffle = CustomShuffle(SimpleShuffleType(""), shuffleEntries)

        deck.Cards[0].Draw(DeckSide.Front, 0, Color(1.0,1.0,1.0,1.0))
        deck.Cards[1].Draw(DeckSide.Left, 1, Color(1.0,1.0,1.0,1.0))

        val expectedSecondBack = listOf<Color>(Color(1.0,1.0,1.0,1.0), Color(), Color(), Color(), Color())
        val expectedFirstRight = listOf<Color>(Color(), Color(1.0,1.0,1.0,1.0), Color(), Color(), Color(), Color(), Color(), Color(), Color(), Color())

        deck.Shuffle(shuffle)

        assertEquals(expectedFirstRight, deck.Cards[0].rightSide)
        assertEquals(expectedSecondBack, deck.Cards[1].backSide)
    }
}