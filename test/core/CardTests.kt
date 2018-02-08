package core

import org.junit.Assert.assertEquals
import org.junit.Test

class CardTests
{
    @Test
    fun testDraw()
    {
        val card = Card(5, 10)

        val expectedFront = listOf<Color>(Color(1.0, 1.0, 1.0, 1.0), Color(), Color(), Color(), Color())
        val expectedBack = listOf<Color>(Color(), Color(1.0, 1.0, 1.0, 1.0), Color(), Color(), Color())
        val expectedLeft = listOf<Color>(Color(), Color(), Color(1.0, 1.0, 1.0, 1.0), Color(), Color(), Color(), Color(), Color(), Color(), Color())
        val expectedRight = listOf<Color>(Color(), Color(), Color(), Color(1.0, 1.0, 1.0, 1.0), Color(), Color(), Color(), Color(), Color(), Color())

        card.Draw(DeckSide.Front, 0, Color(1.0, 1.0, 1.0, 1.0))
        assertEquals(expectedFront, card.frontSide)

        card.Draw(DeckSide.Back, 1, Color(1.0, 1.0, 1.0, 1.0))
        assertEquals(expectedBack, card.backSide)

        card.Draw(DeckSide.Left, 2, Color(1.0, 1.0, 1.0, 1.0))
        assertEquals(expectedLeft, card.leftSide)

        card.Draw(DeckSide.Right, 3, Color(1.0, 1.0, 1.0, 1.0))
        assertEquals(expectedRight, card.rightSide)
    }

    @Test
    fun testTurn()
    {
        val card = Card(5, 10)

        val expectedBack = listOf<Color>(Color(1.0, 1.0, 1.0, 1.0), Color(), Color(), Color(), Color())
        val expectedFront = listOf<Color>(Color(), Color(1.0, 1.0, 1.0, 1.0), Color(), Color(), Color())
        val expectedRight = listOf<Color>(Color(), Color(), Color(1.0, 1.0, 1.0, 1.0), Color(), Color(), Color(), Color(), Color(), Color(), Color())
        val expectedLeft = listOf<Color>(Color(), Color(), Color(), Color(1.0, 1.0, 1.0, 1.0), Color(), Color(), Color(), Color(), Color(), Color())

        card.Draw(DeckSide.Front, 0, Color(1.0, 1.0, 1.0, 1.0))
        card.Draw(DeckSide.Back, 1, Color(1.0, 1.0, 1.0, 1.0))
        card.Draw(DeckSide.Left, 2, Color(1.0, 1.0, 1.0, 1.0))
        card.Draw(DeckSide.Right, 3, Color(1.0, 1.0, 1.0, 1.0))

        card.Turn()

        assertEquals(expectedFront, card.frontSide)
        assertEquals(expectedBack, card.backSide)
        assertEquals(expectedLeft, card.leftSide)
        assertEquals(expectedRight, card.rightSide)
    }

    @Test
    fun testFlip()
    {
        val card = Card(5, 10)

        val expectedFront = listOf<Color>(Color(), Color(), Color(), Color(), Color(1.0, 1.0, 1.0, 1.0))
        val expectedBack = listOf<Color>(Color(), Color(), Color(), Color(1.0, 1.0, 1.0, 1.0), Color())
        val expectedRight = listOf<Color>(Color(), Color(), Color(), Color(), Color(), Color(), Color(), Color(1.0, 1.0, 1.0, 1.0), Color(), Color())
        val expectedLeft = listOf<Color>(Color(), Color(), Color(), Color(), Color(), Color(), Color(1.0, 1.0, 1.0, 1.0), Color(), Color(), Color())

        card.Draw(DeckSide.Front, 0, Color(1.0, 1.0, 1.0, 1.0))
        card.Draw(DeckSide.Back, 1, Color(1.0, 1.0, 1.0, 1.0))
        card.Draw(DeckSide.Left, 2, Color(1.0, 1.0, 1.0, 1.0))
        card.Draw(DeckSide.Right, 3, Color(1.0, 1.0, 1.0, 1.0))

        card.Flip()

        assertEquals(expectedFront, card.frontSide)
        assertEquals(expectedBack, card.backSide)
        assertEquals(expectedLeft, card.leftSide)
        assertEquals(expectedRight, card.rightSide)
    }
}