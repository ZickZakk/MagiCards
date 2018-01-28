package presentation.models

import core.shuffeling.Shuffle
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.io.Serializable

class ShuffleHistoryEntry(var name: String, val Shuffle: Shuffle) : Serializable
{
    @Transient var nameProperty = SimpleStringProperty(name)

    @Transient var shuffleTypeProperty = SimpleStringProperty(Shuffle.type.name)

    init
    {
        nameProperty.onChange { name = nameProperty.get() }
    }

    fun recreate() : ShuffleHistoryEntry
    {
        return ShuffleHistoryEntry(name, Shuffle)
    }
}