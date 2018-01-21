package core.shuffeling

interface Shuffle {
    val Name: String
    val ShuffleEntries: List<ShuffleEntry>

    fun Combine(otherShuffle: Shuffle) : Shuffle
    {
        return CombinedShuffle(this, otherShuffle)
    }

    fun Reverse() : Shuffle
    {
        return ReversedShuffle(this)
    }
}

