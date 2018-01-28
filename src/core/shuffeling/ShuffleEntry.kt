package core.shuffeling

import java.io.Serializable

class ShuffleEntry(val SourceIndex: Int, val DestinationIndex: Int, val Flip: Boolean, val Turn: Boolean) : Serializable
