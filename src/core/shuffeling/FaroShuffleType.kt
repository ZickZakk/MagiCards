package core.shuffeling

class FaroShuffleType : ShuffleType
{
    override fun worksForDeckSize(deckSize: Int): Boolean
    {
        return true
    }

    override fun createShuffleForDeckSize(deckSize: Int): Shuffle
    {
        return FaroShuffle(deckSize, this)
    }

    override val name = "Faro without Flip/Turn"
}