package core.shuffeling

class ReversedShuffle(shuffle: Shuffle) : Shuffle
{
    override val type = SimpleShuffleType( "Reversed " + shuffle.type.name)

    override val shuffleEntries = shuffle.shuffleEntries.map { ShuffleEntry(it.DestinationIndex, it.SourceIndex, it.Flip, it.Turn) }
}