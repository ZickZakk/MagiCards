package core.shuffeling

class FaroShuffle(private val deckSize: Int) : Shuffle {
    override val Name = "Faro without Flip/Turn"
    override val ShuffleEntries = List(deckSize, { index -> ShuffleEntry(index, faroShuffleLogic(index), false, false) })

    private fun faroShuffleLogic(source : Int) : Int
    {
        if(source % 2 == 0)
            return source / 2

        return (source / 2) + (deckSize / 2)
    }
}