package presentation.components

import core.Deck
import core.DeckSide
import core.shuffeling.Shuffle
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import tornadofx.*

class DeckSideImage(private val _deck: Deck) {

    private var _currentSide = DeckSide.Left

    val zoomLevel = SimpleIntegerProperty(3)

    private var _image = WritableImage(_deck.SideWidth, _deck.Size * 2)

    val imageProperty = SimpleObjectProperty<Image>(_image)

    init {
        zoomLevel.onChange { refreshImage() }
        refreshImage()
    }

    fun draw(x: Int, y: Int, color: Color) {
        val realX = x / zoomLevel.get()
        val realY = y / (zoomLevel.get() + 1)

        if(realX >= _deck.getSideWidth(_currentSide) || realX < 0)
            return

        if(realY >= _deck.Size || realY < 0)
            return

        _deck.Cards[realY].Draw(_currentSide, realX, color)
        refreshImage()
    }

    private fun refreshImage() {
        _image = WritableImage(_deck.getSideWidth(_currentSide) * zoomLevel.get(), _deck.Size * (1 + zoomLevel.get()))
        for (card in _deck.Cards.withIndex())
            for (color in card.value.Side(_currentSide).withIndex())
                for (zoomOffsetX in 0 until zoomLevel.get())
                    for (zoomOffsetY in 0 until zoomLevel.get())
                        _image.pixelWriter.setColor(color.index * zoomLevel.get() + zoomOffsetX, card.index * (1 + zoomLevel.get()) + zoomOffsetY, color.value)
        imageProperty.set(_image)
    }

    fun shuffle(shuffle: Shuffle)
    {
        _deck.Shuffle(shuffle.ShuffleEntries)
        refreshImage()
    }
}