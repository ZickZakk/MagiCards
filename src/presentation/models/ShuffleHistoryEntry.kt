package presentation.models

import core.shuffeling.Shuffle
import javafx.beans.property.SimpleStringProperty

class ShuffleHistoryEntry(name: String, val Shuffle: Shuffle)
{
    val nameProperty = SimpleStringProperty(name)

    val shuffleTypeProperty = SimpleStringProperty(Shuffle.type.name)

    var name: String
        get()
        {
            return nameProperty.get()
        }
        set(value)
        {
            nameProperty.set(value)
        }
}