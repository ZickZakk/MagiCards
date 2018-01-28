package core.shuffeling

import java.io.Serializable

interface Shuffle : Serializable
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

