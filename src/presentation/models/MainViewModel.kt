package presentation.models

import core.Deck
import core.DeckSide
import core.shuffeling.FaroShuffle
import core.shuffeling.NonChangingShuffle
import core.shuffeling.Shuffle
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import javafx.scene.control.TextInputDialog
import javafx.scene.image.Image
import javafx.scene.paint.Color
import presentation.components.DeckSideImage
import tornadofx.*


class MainViewModel(private val _deck: Deck)
{
    // Outputs
    val deckSideImageProperty = SimpleObjectProperty<Image>()

    // Inputs
    val selectedDrawingColorProperty = SimpleObjectProperty<Color>()
    val drawModeEnabledProperty = SimpleBooleanProperty()
    val eraseModeEnabledProperty = SimpleBooleanProperty()
    val selectedZoomLevelProperty = SimpleIntegerProperty(3)
    val selectedDeckSideProperty = SimpleObjectProperty<DeckSide>(DeckSide.Left)
    val selectedShuffleProperty = SimpleObjectProperty<ShuffleHistoryEntry>()

    val shuffles = FXCollections.observableArrayList<ShuffleHistoryEntry>()!!

    private var _combinedShuffleState: Shuffle = NonChangingShuffle(_deck.Size)

    init
    {
        selectedDeckSideProperty.onChange { redraw() }
        selectedZoomLevelProperty.onChange { redraw() }
        selectedShuffleProperty.onChange { shuffleTo(it!!) }

        shuffles.add(ShuffleHistoryEntry("Start", NonChangingShuffle(_deck.Size)))
    }

    private fun shuffleTo(shuffleHistoryEntry: ShuffleHistoryEntry)
    {
        _deck.Shuffle(_combinedShuffleState.Reverse())

        _combinedShuffleState = NonChangingShuffle(_deck.Size)

        for (shuffle in shuffles.dropLastWhile { it != shuffleHistoryEntry })
        {
            _combinedShuffleState = _combinedShuffleState.Combine(shuffle.Shuffle)
        }

        _deck.Shuffle(_combinedShuffleState)

        redraw()
    }

    fun interactWithImage(viewX: Int, viewY: Int)
    {
        if (!drawModeEnabledProperty.get() && !eraseModeEnabledProperty.get())
            return

        val realX = viewX / selectedZoomLevelProperty.get()
        val cardIndex = viewY / (selectedZoomLevelProperty.get() + 1)

        if (realX >= _deck.getSideWidth(selectedDeckSideProperty.get()) || realX < 0)
            return

        if (cardIndex >= _deck.Size || cardIndex < 0)
            return

        if (drawModeEnabledProperty.get())
            _deck.Cards[cardIndex].Draw(selectedDeckSideProperty.get(), realX, selectedDrawingColorProperty.get())

        if (eraseModeEnabledProperty.get())
            _deck.Cards[cardIndex].Draw(selectedDeckSideProperty.get(), realX, Color.WHITE)

        redraw()
    }

    private fun redraw()
    {
        deckSideImageProperty.set(DeckSideImage(_deck, selectedDeckSideProperty.get(), selectedZoomLevelProperty.get()).image)
    }

    fun addShuffleToHistory(name: String)
    {
        shuffles.add(ShuffleHistoryEntry(name, FaroShuffle(_deck.Size)))
    }

    fun renameShuffleHistoryEntry()
    {
        val oldName = selectedShuffleProperty.get().Name

        val dialog = TextInputDialog(oldName)
        dialog.title = "Rename Shuffle History Entry '$oldName'"
        dialog.headerText = "Rename Shuffle History Entry '$oldName'"
        dialog.contentText = "New Name:"

        val result = dialog.showAndWait()

        result.ifPresent { selectedShuffleProperty.get().Name = it }
    }

    fun removeShuffleHistoryEntry()
    {
        val nameToRemove = selectedShuffleProperty.get().Name

        val alert = Alert(AlertType.CONFIRMATION)

        alert.title = "Remove Shuffle '$nameToRemove'"
        alert.headerText = "Remove Shuffle '$nameToRemove'"
        alert.contentText = "Do you really want to remove this shuffle?"

        val result = alert.showAndWait()

        if (result.get() == ButtonType.OK)
        {
            shuffles.remove(selectedShuffleProperty.get())
        }
    }

    fun moveUpShuffleHistoryEntry()
    {
        shuffles.moveUp(selectedShuffleProperty.get())
    }

    fun moveDownShuffleHistoryEntry()
    {
        shuffles.moveDown(selectedShuffleProperty.get())
    }
}