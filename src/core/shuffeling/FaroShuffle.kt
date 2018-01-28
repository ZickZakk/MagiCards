package core.shuffeling

class FaroShuffle(private val deckSize: Int, override val type: ShuffleType) : Shuffle
{
    override val shuffleEntries = List(deckSize, { index -> ShuffleEntry(index, faroShuffleLogic(index), false, false) })

    private fun faroShuffleLogic(source: Int): Int
    {
        if (source % 2 == 0)
            return source / 2

        return (source / 2) + (deckSize / 2)
    }
}