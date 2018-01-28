package presentation.components

import core.Deck
import core.DeckSide
import javafx.scene.image.Image
import javafx.scene.image.WritableImage

class DeckSideImage(deck: Deck, deckSide: DeckSide, zoom: Int)
{
    val image: Image

    init
    {
        image = WritableImage(deck.getSideWidth(deckSide) * zoom, deck.size * (1 + zoom))
        for (card in deck.Cards.withIndex())
            for (color in card.value.Side(deckSide).withIndex())
                for (zoomOffsetX in 0 until zoom)
                    for (zoomOffsetY in 0 until zoom)
                        image.pixelWriter.setColor(color.index * zoom + zoomOffsetX, card.index * (1 + zoom) + zoomOffsetY, color.value.fxColor)
    }
}