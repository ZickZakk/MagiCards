package presentation.models

import core.Deck
import core.DeckSide
import core.shuffeling.NonChangingShuffle
import core.shuffeling.Shuffle
import core.shuffeling.ShuffleRegister
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import javafx.scene.control.ChoiceDialog
import javafx.scene.control.TextInputDialog
import javafx.scene.image.Image
import javafx.scene.paint.Color
import org.jdom2.input.DOMBuilder
import org.jonnyzzz.kotlin.xml.bind.jdom.JDOM
import presentation.components.DeckSideImage
import tornadofx.*
import java.io.*
import javax.xml.parsers.DocumentBuilderFactory


class MainViewModel
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
    val currentFilePath = SimpleStringProperty("")

    private var _deck = Deck(52, 60, 260)

    val shuffles = FXCollections.observableArrayList<ShuffleHistoryEntry>()!!

    private var _combinedShuffleState: Shuffle = NonChangingShuffle(_deck.size)

    init
    {
        selectedDeckSideProperty.onChange { redraw() }
        selectedZoomLevelProperty.onChange { redraw() }
        selectedShuffleProperty.onChange { shuffleTo(it!!) }
        shuffles.onChange { redraw() }

        shuffles.add(ShuffleHistoryEntry("Start", NonChangingShuffle(_deck.size)))

        reloadShuffles()
    }

    private fun shuffleTo(shuffleHistoryEntry: ShuffleHistoryEntry)
    {
        _deck.Shuffle(_combinedShuffleState.reverse())

        _combinedShuffleState = NonChangingShuffle(_deck.size)

        for (shuffle in shuffles.dropLastWhile { it != shuffleHistoryEntry })
        {
            _combinedShuffleState = _combinedShuffleState.combine(shuffle.Shuffle)
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

        if (cardIndex >= _deck.size || cardIndex < 0)
            return

        if (drawModeEnabledProperty.get())
            _deck.Cards[cardIndex].Draw(selectedDeckSideProperty.get(), realX, core.Color(selectedDrawingColorProperty.get()))

        if (eraseModeEnabledProperty.get())
            _deck.Cards[cardIndex].Draw(selectedDeckSideProperty.get(), realX, core.Color())

        redraw()
    }

    private fun redraw()
    {
        deckSideImageProperty.set(DeckSideImage(_deck, selectedDeckSideProperty.get(), selectedZoomLevelProperty.get()).image)
    }

    fun addShuffleToHistory()
    {
        val choices = ShuffleRegister.registeredShuffleTypes.map { it.name }

        val dialog = ChoiceDialog(ShuffleRegister.registeredShuffleTypes.first().name, choices)
        dialog.title = "Choose shuffle to add"
        dialog.headerText = "Choose shuffle to add"
        dialog.contentText = "Registered shuffles:"

        val result = dialog.showAndWait()

        result.ifPresent { chosenShuffleName ->
            val shuffleToAdd = ShuffleRegister.registeredShuffleTypes.first({ it.name == chosenShuffleName }).createShuffleForDeckSize(_deck.size)
            shuffles.add(ShuffleHistoryEntry("New shuffle", shuffleToAdd))
        }
    }

    fun renameShuffleHistoryEntry()
    {
        val oldName = selectedShuffleProperty.get().nameProperty.get()

        val dialog = TextInputDialog(oldName)
        dialog.title = "Rename Shuffle History Entry '$oldName'"
        dialog.headerText = "Rename Shuffle History Entry '$oldName'"
        dialog.contentText = "New name:"

        val result = dialog.showAndWait()

        result.ifPresent { selectedShuffleProperty.get().nameProperty.set(it) }
    }

    fun removeShuffleHistoryEntry()
    {
        val nameToRemove = selectedShuffleProperty.get().nameProperty.get()

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

    fun reloadShuffles()
    {
        val jarPath = File(MainViewModel::class.java.protectionDomain.codeSource.location.toURI().path)

        val shufflesDirectory = jarPath.resolve(File("shuffles"))

        for (file in shufflesDirectory.walkTopDown())
        {
            if (!file.isFile)
                continue

            if (!file.canRead())
                continue

            if (!file.path.endsWith(".shuffle"))
                continue

            val xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)

            val document = DOMBuilder().build(xmlDoc)

            val loadedShuffle = JDOM.load(document.rootElement, XMLShuffle::class.java)

            if (!loadedShuffle.isValid())
                continue

            ShuffleRegister.addShuffleType(loadedShuffle.toShuffleType())
        }
    }

    fun save()
    {
        saveAs(File(currentFilePath.get()))
    }

    fun saveAs(file: File)
    {
        _deck.Shuffle(_combinedShuffleState.reverse())

        val currentState = WorkBundle(_deck, shuffles.drop(1).toList())

        ObjectOutputStream(FileOutputStream(file)).use { it -> it.writeObject(currentState) }

        _deck.Shuffle(_combinedShuffleState)

        currentFilePath.set(file.absolutePath)
    }

    fun open(file: File)
    {
        _deck.Shuffle(_combinedShuffleState.reverse())
        _combinedShuffleState = NonChangingShuffle(_deck.size)

        var workBundle: WorkBundle

        ObjectInputStream(FileInputStream(file)).use { it ->
            workBundle = it.readObject() as WorkBundle
            _deck = workBundle.deck
            shuffles.remove(1, shuffles.size)
            shuffles.addAll(workBundle.shuffles.map { it.recreate() })
        }

        redraw()

        currentFilePath.set(file.absolutePath)
    }

    fun newDeck(deckSize: Int, faceWidth: Int, sideWidth: Int)
    {
        _deck.Shuffle(_combinedShuffleState.reverse())
        _combinedShuffleState = NonChangingShuffle(_deck.size)

        _deck = Deck(deckSize, faceWidth, sideWidth)
        shuffles.remove(1, shuffles.size)

        redraw()

        currentFilePath.set("")
    }
}