package core.shuffeling

class CustomShuffleType(override val name: String, private val shuffleEntries: List<ShuffleEntry>) : ShuffleType
{
    override fun worksForDeckSize(deckSize: Int): Boolean
    {
        return deckSize == shuffleEntries.size
    }

    override fun createShuffleForDeckSize(deckSize: Int): Shuffle
    {
        return CustomShuffle(this, shuffleEntries)
    }
}