package core.shuffeling

import java.io.Serializable

interface ShuffleType : Serializable
{
    val name: String

    fun worksForDeckSize(deckSize: Int) : Boolean

    fun createShuffleForDeckSize(deckSize: Int) : Shuffle
}