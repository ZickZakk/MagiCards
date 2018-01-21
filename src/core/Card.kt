package core

import javafx.scene.paint.Color

class Card(FaceWidth: Int, SideWidth: Int) {

    private val _sides =
            mutableMapOf(DeckSide.Front to MutableList<Color>(FaceWidth, { Color.WHITE }),
                    DeckSide.Back to MutableList<Color>(FaceWidth, { Color.WHITE }),
                    DeckSide.Left to MutableList<Color>(SideWidth, { Color.WHITE }),
                    DeckSide.Right to MutableList<Color>(SideWidth, { Color.WHITE }))

    val frontSide: List<Color>
        get() = _sides[DeckSide.Front]!!

    val backSide: List<Color>
        get() = _sides[DeckSide.Back]!!

    val rightSide: List<Color>
        get() = _sides[DeckSide.Right]!!

    val leftSide: List<Color>
        get() = _sides[DeckSide.Left]!!

    fun Turn() {
        _sides[DeckSide.Front] = _sides[DeckSide.Back]!!.also { _sides[DeckSide.Back] = _sides[DeckSide.Front]!! }
        _sides[DeckSide.Left] = _sides[DeckSide.Right]!!.also { _sides[DeckSide.Right] = _sides[DeckSide.Left]!! }
    }

    fun Flip() {
        _sides[DeckSide.Left] = _sides[DeckSide.Right]!!.also { _sides[DeckSide.Right] = _sides[DeckSide.Left]!! }
    }

    fun Draw(side: DeckSide, pixel: Int, color: Color) {
        _sides[side]!![pixel] = color
    }

    fun Side(side: DeckSide) : List<Color>
    {
        return _sides[side]!!
    }
}