package core.shuffeling

class NonChangingShuffle(deckSize: Int) : Shuffle
{
    override val Name = "None"
    override val ShuffleEntries = List(deckSize, { index -> ShuffleEntry(index, index, false, false) })
}