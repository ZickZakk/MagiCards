package core.shuffeling

class ReversedShuffle(shuffle: Shuffle) : Shuffle
{
    override val Name = "Reversed " + shuffle.Name
    override val ShuffleEntries = shuffle.ShuffleEntries.map { ShuffleEntry(it.DestinationIndex, it.SourceIndex, it.Flip, it.Turn) }
}