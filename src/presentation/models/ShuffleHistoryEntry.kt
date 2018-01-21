package presentation.models

import core.shuffeling.Shuffle
import javafx.beans.property.SimpleStringProperty

class ShuffleHistoryEntry(val Name: String, val Shuffle: Shuffle)
{
    val nameProperty = SimpleStringProperty(Name)

    val shuffleTypeProperty = SimpleStringProperty(Shuffle.Name)
}