package presentation.views

import core.DeckSide
import javafx.beans.binding.Bindings
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.scene.Cursor
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import presentation.components.DeckSideImage
import presentation.models.MainViewModel
import presentation.models.ShuffleHistoryEntry
import tornadofx.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert
import java.util.Collections.addAll
import javafx.scene.control.Hyperlink
import javafx.scene.layout.FlowPane






class MainView : View()
{
    override val root: VBox by fxml()

    private val _deckView: ImageView by fxid("deckView")
    private val _drawToggleButton: ToggleButton by fxid("drawToggleButton")
    private val _eraseToggleButton: ToggleButton by fxid("eraseToggleButton")
    private val _colorPicker: ColorPicker by fxid("colorPicker")
    private val _zoomSlider: Slider by fxid("zoomSlider")

    private val _shuffleHistoryTable: TableView<ShuffleHistoryEntry> by fxid("shuffleHistoryTable")
    private val _shuffleHistoryNameColumn: TableColumn<ShuffleHistoryEntry, String> by fxid("shuffleHistoryNameColumn")
    private val _shuffleHistoryTypeColumn: TableColumn<ShuffleHistoryEntry, String> by fxid("shuffleHistoryTypeColumn")

    private val _addShuffleButton: Button by fxid("addShuffleButton")
    private val _renameShuffleButton: Button by fxid("renameShuffleButton")
    private val _upShuffleButton: Button by fxid("upShuffleButton")
    private val _downShuffleButton: Button by fxid("downShuffleButton")
    private val _removeShuffleButton: Button by fxid("removeShuffleButton")

    private val _saveMenuButton: MenuItem by fxid("saveMenuButton")

    private val _sideChooser: ChoiceBox<DeckSide> by fxid("sideChooser")

    private val _drawToggleGroup = ToggleGroup()

    private val _viewModel = MainViewModel()

    init
    {
        _deckView.imageProperty().bind(_viewModel.deckSideImageProperty)
        _viewModel.selectedZoomLevelProperty.bind(_zoomSlider.valueProperty())

        _viewModel.drawModeEnabledProperty.bind(_drawToggleButton.selectedProperty())
        _viewModel.eraseModeEnabledProperty.bind(_eraseToggleButton.selectedProperty())

        _viewModel.selectedDrawingColorProperty.bind(_colorPicker.valueProperty())

        root.cursorProperty().bind(Bindings.`when`(_viewModel.drawModeEnabledProperty.or(_viewModel.eraseModeEnabledProperty)).then(Cursor.CROSSHAIR).otherwise(Cursor.DEFAULT))
        _deckView.cursorProperty().bind(Bindings.`when`(_viewModel.drawModeEnabledProperty.or(_viewModel.eraseModeEnabledProperty)).then(Cursor.CROSSHAIR).otherwise(Cursor.DEFAULT))

        _drawToggleButton.toggleGroup = _drawToggleGroup
        _eraseToggleButton.toggleGroup = _drawToggleGroup

        _shuffleHistoryNameColumn.setCellValueFactory { it.value.nameProperty }
        _shuffleHistoryTypeColumn.setCellValueFactory { it.value.shuffleTypeProperty }

        _shuffleHistoryTable.bindSelected(_viewModel.selectedShuffleProperty)
        _shuffleHistoryTable.items = _viewModel.shuffles
        _shuffleHistoryTable.selectFirst()

        _renameShuffleButton.disableProperty().bind(_shuffleHistoryTable.selectionModel.selectedIndexProperty().eq(0))
        _upShuffleButton.disableProperty().bind(_shuffleHistoryTable.selectionModel.selectedIndexProperty().lessThanOrEqualTo(1))
        _downShuffleButton.disableProperty().bind(_shuffleHistoryTable.selectionModel.selectedIndexProperty().eq(0))
        _removeShuffleButton.disableProperty().bind(_shuffleHistoryTable.selectionModel.selectedIndexProperty().eq(0))

        _deckView.setOnScroll { event ->
            if (event.isControlDown)
            {
                _zoomSlider.adjustValue(_zoomSlider.value + (event.deltaY / event.multiplierY))
            }
        }

        _saveMenuButton.disableProperty().bind(_viewModel.currentFilePath.isEmpty)

        titleProperty.bind(Bindings.concat("MagiCards - ", _viewModel.currentFilePath))

        _sideChooser.items.setAll(DeckSide.values().toList())

        _sideChooser.valueProperty().bindBidirectional(_viewModel.selectedDeckSideProperty)
    }

    fun interactWithImage(event: MouseEvent)
    {
        _viewModel.interactWithImage(event.x.toInt(), event.y.toInt())
    }

    fun addNewShuffleToHistory()
    {
        _viewModel.addShuffleToHistory()
        _shuffleHistoryTable.selectionModel.selectLast()
    }

    fun renameShuffleHistoryEntry()
    {
        _viewModel.renameShuffleHistoryEntry()
    }

    fun removeShuffleHistoryEntry()
    {
        _viewModel.removeShuffleHistoryEntry()
    }

    fun moveUpShuffleHistoryEntry()
    {
        _viewModel.moveUpShuffleHistoryEntry()
    }

    fun moveDownShuffleHistoryEntry()
    {
        _viewModel.moveDownShuffleHistoryEntry()
    }

    fun saveAs()
    {
        val fileChooser = FileChooser()

        //Set extension filter
        val extFilter = FileChooser.ExtensionFilter("Deck save files (*.deck)", "*.deck")
        fileChooser.extensionFilters.add(extFilter)

        val allFilter = FileChooser.ExtensionFilter("All files (*)", "*")
        fileChooser.extensionFilters.add(allFilter)

        fileChooser.initialFileName = "*.deck"

        //Show save file dialog
        val file = fileChooser.showSaveDialog(currentStage) ?: return

        _viewModel.saveAs(file)
    }

    fun save()
    {
        _viewModel.save()
    }

    fun newDeck()
    {
        _viewModel.newDeck(52, 60, 260)
    }

    fun open()
    {
        val fileChooser = FileChooser()

        //Set extension filter
        val extFilter = FileChooser.ExtensionFilter("Deck save files (*.deck)", "*.deck")
        fileChooser.extensionFilters.add(extFilter)

        val allFilter = FileChooser.ExtensionFilter("All files (*)", "*")
        fileChooser.extensionFilters.add(allFilter)

        //Show save file dialog
        val file = fileChooser.showOpenDialog(currentStage) ?: return

        _viewModel.open(file)

        _shuffleHistoryTable.selectionModel.selectFirst()
    }

    fun quit()
    {
        primaryStage.close()
    }

    fun showInfo()
    {
        val alert = Alert(AlertType.INFORMATION)
        alert.title = "MagiCards Information"
        alert.headerText = "MagiCards Information"

        val box = VBox()
        val line1 = Label("This tool was developed by Georg Jenschmischek.")
        val line2 = Label("It is licenced for free under GPL v3.")
        val line3 = Label("Visit https://github.com/ZickZakk/MagiCards for source code.")

        box.children.addAll(line1, line2, line3)

        alert.dialogPane.contentProperty().set(box)

        alert.showAndWait()
    }
}
