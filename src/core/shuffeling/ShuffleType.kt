package core.shuffeling

interface ShuffleType
{
    val name: String

    fun worksForDeckSize(deckSize: Int) : Boolean

    fun createShuffleForDeckSize(deckSize: Int) : Shuffle
}