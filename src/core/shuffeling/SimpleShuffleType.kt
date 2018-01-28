package core.shuffeling

class SimpleShuffleType(override val name: String) : ShuffleType
{
    override fun createShuffleForDeckSize(deckSize: Int): Shuffle
    {
        return NonChangingShuffle(deckSize)
    }

    override fun worksForDeckSize(deckSize: Int): Boolean
    {
        return false
    }
}