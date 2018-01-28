package core.shuffeling

class CombinedShuffle(firstShuffle: Shuffle, secondShuffle: Shuffle) : Shuffle
{
    override val type = SimpleShuffleType(firstShuffle.type.name + " combined with " + secondShuffle.type.name)

    override val shuffleEntries: List<ShuffleEntry>

    init
    {
        if(firstShuffle.shuffleEntries.size != secondShuffle.shuffleEntries.size)
            throw IllegalArgumentException("Shuffles to combine are not made for the same deck size!")

        shuffleEntries = mutableListOf()

        for (shuffleEntry in firstShuffle.shuffleEntries)
        {
            val otherShuffleEntry = secondShuffle.shuffleEntries.find { it.SourceIndex == shuffleEntry.DestinationIndex }!!

            val combinedShuffleEntry = ShuffleEntry(shuffleEntry.SourceIndex,
                    otherShuffleEntry.DestinationIndex,
                    shuffleEntry.Flip.xor(otherShuffleEntry.Flip),
                    shuffleEntry.Turn.xor(otherShuffleEntry.Turn))

            shuffleEntries.add(combinedShuffleEntry)
        }
    }
}