package presentation

import core.Deck
import core.shuffeling.FaroShuffle
import core.shuffeling.NonChangingShuffle
import core.shuffeling.Shuffle
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import javafx.scene.Cursor
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import presentation.components.DeckSideImage
import presentation.models.ShuffleHistoryEntry
import tornadofx.*

class MainView : View() {

    override val root : VBox by fxml("../MagiCards.fxml")
    private val deckView : ImageView by fxid()
    private val drawMenu : TitledPane by fxid()
    private val sideMenu : Accordion by fxid()
    private val drawToggleButton : ToggleButton by fxid()
    private val eraseToggleButton : ToggleButton by fxid()
    private val colorPicker : ColorPicker by fxid()
    private val zoomSlider : Slider by fxid()
    private val shuffleHistoryTable : TableView<ShuffleHistoryEntry> by fxid()
    private val shuffleHistoryNameColumn : TableColumn<ShuffleHistoryEntry, String> by fxid()
    private val shuffleHistoryTypeColumn : TableColumn<ShuffleHistoryEntry, String> by fxid()


    private val drawModeActive = SimpleBooleanProperty()
    private val eraseModeActive = SimpleBooleanProperty()

    private val toggleGroup = ToggleGroup()


    private val deckSize = 52

    private val _deck = DeckSideImage(Deck(deckSize, 50, 260))

    private val _shuffles = FXCollections.observableArrayList<ShuffleHistoryEntry>()

    init {
        deckView.imageProperty().bind(_deck.imageProperty)
        sideMenu.expandedPane = drawMenu
        _deck.zoomLevel.bind(zoomSlider.valueProperty())

        drawToggleButton.toggleGroup = toggleGroup
        eraseToggleButton.toggleGroup = toggleGroup
        drawModeActive.bind(drawToggleButton.selectedProperty())
        eraseModeActive.bind(eraseToggleButton.selectedProperty())

        root.cursorProperty().bind(Bindings.`when`(drawModeActive.or(eraseModeActive)).then(Cursor.CROSSHAIR).otherwise(Cursor.DEFAULT))
        deckView.cursorProperty().bind(Bindings.`when`(drawModeActive.or(eraseModeActive)).then(Cursor.CROSSHAIR).otherwise(Cursor.DEFAULT))

        shuffleHistoryNameColumn.setCellValueFactory { it.value.nameProperty }
        shuffleHistoryTypeColumn.setCellValueFactory { it.value.shuffleTypeProperty }
        _shuffles.add(ShuffleHistoryEntry("Start", NonChangingShuffle(deckSize)))

        shuffleHistoryTable.items = _shuffles
        shuffleHistoryTable.selectFirst()
        shuffleHistoryTable.selectionModel.selectedIndexProperty().addListener { _, oldIndex, newIndex -> shuffleDeckImage(oldIndex.toInt(), newIndex.toInt()) }
    }

    fun interactWithImage(event: MouseEvent)
    {
        if(!drawModeActive.get() && !eraseModeActive.get())
            return

        if(drawModeActive.get())
            _deck.draw(event.x.toInt(), event.y.toInt(), colorPicker.value)

        if(eraseModeActive.get())
            _deck.draw(event.x.toInt(), event.y.toInt(), Color.WHITE)
    }

    fun addNewShuffleToHistory()
    {
        _shuffles.add(ShuffleHistoryEntry("Test",FaroShuffle(deckSize)))
        shuffleHistoryTable.selectionModel.selectLast()
    }

    fun shuffleDeckImage(oldShuffleIndex: Int, newShuffleIndex: Int)
    {
        var shuffle : Shuffle = NonChangingShuffle(deckSize)

        var start = oldShuffleIndex
        var end = newShuffleIndex

        if(oldShuffleIndex > newShuffleIndex)
            start = end.also{end = start}

        // Skip the current deck shuffle
        start++

        for (index in start..end)
        {
            shuffle = shuffle.Combine(_shuffles[index]!!.Shuffle)
        }

        if(oldShuffleIndex > newShuffleIndex)
            shuffle = shuffle.Reverse()

        _deck.shuffle(shuffle)
    }
}
