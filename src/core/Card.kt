package core

import java.io.Serializable

class Card(FaceWidth: Int, SideWidth: Int) : Serializable
{

    private val _sides =
            mutableMapOf(DeckSide.Front to MutableList(FaceWidth, { Color() }),
                    DeckSide.Back to MutableList(FaceWidth, { Color() }),
                    DeckSide.Left to MutableList(SideWidth, { Color() }),
                    DeckSide.Right to MutableList(SideWidth, { Color() }))

    val frontSide: List<Color>
        get() = _sides[DeckSide.Front]!!

    val backSide: List<Color>
        get() = _sides[DeckSide.Back]!!

    val rightSide: List<Color>
        get() = _sides[DeckSide.Right]!!

    val leftSide: List<Color>
        get() = _sides[DeckSide.Left]!!

    fun Turn()
    {
        _sides[DeckSide.Front] = _sides[DeckSide.Back]!!.also { _sides[DeckSide.Back] = _sides[DeckSide.Front]!! }
        _sides[DeckSide.Left] = _sides[DeckSide.Right]!!.also { _sides[DeckSide.Right] = _sides[DeckSide.Left]!! }
    }

    fun Flip()
    {
        _sides[DeckSide.Left] = _sides[DeckSide.Right]!!.also { _sides[DeckSide.Right] = _sides[DeckSide.Left]!! }

        _sides[DeckSide.Front]!!.reverse()
        _sides[DeckSide.Back]!!.reverse()
        _sides[DeckSide.Left]!!.reverse()
        _sides[DeckSide.Right]!!.reverse()
    }

    fun Draw(side: DeckSide, pixel: Int, color: Color)
    {
        _sides[side]!![pixel] = color
    }

    fun Side(side: DeckSide): List<Color>
    {
        return _sides[side]!!
    }
}