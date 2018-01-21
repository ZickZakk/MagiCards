package core.shuffeling

class ReversedShuffle(shuffle: Shuffle) : Shuffle {
    override val Name = "Reversed"
    override val ShuffleEntries = shuffle.ShuffleEntries.map { ShuffleEntry(it.DestinationIndex, it.SourceIndex, it.Flip, it.Turn) }
}