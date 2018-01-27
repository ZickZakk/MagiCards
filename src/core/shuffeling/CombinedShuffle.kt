package core.shuffeling

class CombinedShuffle(firstShuffle: Shuffle, secondShuffle: Shuffle) : Shuffle
{
    override val Name = firstShuffle.Name + " combined with " + secondShuffle.Name
    override val ShuffleEntries: List<ShuffleEntry>

    init
    {
        ShuffleEntries = mutableListOf()

        for (shuffleEntry in firstShuffle.ShuffleEntries)
        {
            val otherShuffleEntry = secondShuffle.ShuffleEntries.find { it.SourceIndex == shuffleEntry.DestinationIndex }!!

            val combinedShuffleEntry = ShuffleEntry(shuffleEntry.SourceIndex,
                    otherShuffleEntry.DestinationIndex,
                    shuffleEntry.Flip.xor(otherShuffleEntry.Flip),
                    shuffleEntry.Turn.xor(otherShuffleEntry.Turn))

            ShuffleEntries.add(combinedShuffleEntry)
        }
    }
}