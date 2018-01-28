package core.shuffeling

interface Shuffle
{
    val type: ShuffleType
    val shuffleEntries: List<ShuffleEntry>

    fun combine(otherShuffle: Shuffle): Shuffle
    {
        return CombinedShuffle(this, otherShuffle)
    }

    fun reverse(): Shuffle
    {
        return ReversedShuffle(this)
    }
}

