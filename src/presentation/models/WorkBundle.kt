package presentation.models

import core.Deck
import java.io.Serializable

data class WorkBundle(val deck: Deck, val shuffles: List<ShuffleHistoryEntry>) : Serializable