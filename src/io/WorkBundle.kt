package io

import core.Deck
import presentation.models.ShuffleHistoryEntry
import java.io.Serializable

data class WorkBundle(val deck: Deck, val shuffles: List<ShuffleHistoryEntry>) : Serializable