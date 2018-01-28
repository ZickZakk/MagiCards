package core.shuffeling

class NonChangingShuffle(deckSize: Int) : Shuffle
{
    override val type = SimpleShuffleType("None")
    override val shuffleEntries = List(deckSize, { index -> ShuffleEntry(index, index, false, false) })
}