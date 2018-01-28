package core

import core.shuffeling.CustomShuffle
import core.shuffeling.ShuffleEntry
import core.shuffeling.SimpleShuffleType
import javafx.scene.paint.Color
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

        deck.Cards[0].Draw(DeckSide.Front, 0, Color.BLUE)
        deck.Cards[1].Draw(DeckSide.Left, 1, Color.BLUE)

        val expectedSecondBack = listOf<Color>(Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)
        val expectedFirstRight = listOf<Color>(Color.WHITE, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)

        deck.Shuffle(shuffle)

        assertEquals(expectedFirstRight, deck.Cards[0].rightSide)
        assertEquals(expectedSecondBack, deck.Cards[1].backSide)
    }
}