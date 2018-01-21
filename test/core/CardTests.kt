package core

import javafx.scene.paint.Color
import org.junit.Assert.assertEquals
import org.junit.Test

class CardTests {
    @Test
    fun testDraw() {
        val card = Card(5, 10)

        val expectedFront = listOf<Color>(Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)
        val expectedBack = listOf<Color>(Color.WHITE, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE)
        val expectedLeft = listOf<Color>(Color.WHITE, Color.WHITE, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)
        val expectedRight = listOf<Color>(Color.WHITE, Color.WHITE, Color.WHITE, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)

        card.Draw(DeckSide.Front, 0, Color.BLUE)
        assertEquals(expectedFront, card.frontSide)

        card.Draw(DeckSide.Back,1, Color.BLUE)
        assertEquals(expectedBack, card.backSide)

        card.Draw(DeckSide.Left,2, Color.BLUE)
        assertEquals(expectedLeft, card.leftSide)

        card.Draw(DeckSide.Right,3, Color.BLUE)
        assertEquals(expectedRight, card.rightSide)
    }

    @Test fun testTurn() {
        val card = Card(5, 10)

        val expectedBack = listOf<Color>(Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)
        val expectedFront = listOf<Color>(Color.WHITE, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE)
        val expectedRight = listOf<Color>(Color.WHITE, Color.WHITE, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)
        val expectedLeft = listOf<Color>(Color.WHITE, Color.WHITE, Color.WHITE, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)

        card.Draw(DeckSide.Front,0, Color.BLUE)
        card.Draw(DeckSide.Back,1, Color.BLUE)
        card.Draw(DeckSide.Left,2, Color.BLUE)
        card.Draw(DeckSide.Right,3, Color.BLUE)

        card.Turn()

        assertEquals(expectedFront, card.frontSide)
        assertEquals(expectedBack, card.backSide)
        assertEquals(expectedLeft, card.leftSide)
        assertEquals(expectedRight, card.rightSide)
    }

    @Test fun testFlip() {
        val card = Card(5, 10)

        val expectedFront = listOf<Color>(Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)
        val expectedBack = listOf<Color>(Color.WHITE, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE)
        val expectedRight = listOf<Color>(Color.WHITE, Color.WHITE, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)
        val expectedLeft = listOf<Color>(Color.WHITE, Color.WHITE, Color.WHITE, Color.BLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)

        card.Draw(DeckSide.Front,0, Color.BLUE)
        card.Draw(DeckSide.Back,1, Color.BLUE)
        card.Draw(DeckSide.Left,2, Color.BLUE)
        card.Draw(DeckSide.Right,3, Color.BLUE)

        card.Flip()

        assertEquals(expectedFront, card.frontSide)
        assertEquals(expectedBack, card.backSide)
        assertEquals(expectedLeft, card.leftSide)
        assertEquals(expectedRight, card.rightSide)
    }
}