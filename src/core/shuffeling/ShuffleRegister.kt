package core.shuffeling

class ShuffleRegister
{
    companion object
    {
        private val register = mutableListOf<ShuffleType>()

        val registeredShuffleTypes: List<ShuffleType> = register

        fun addShuffleType(shuffleType: ShuffleType)
        {
            register.removeIf { it.name == shuffleType.name }

            register.add(shuffleType)
        }
    }
}