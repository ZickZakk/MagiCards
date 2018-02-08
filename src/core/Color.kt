package core

import javafx.scene.paint.Color
import java.io.Serializable

class Color() : Serializable
{
    private var red: Double = 1.toDouble()
    private var green: Double = 1.toDouble()
    private var blue: Double = 1.toDouble()
    private var alpha: Double = 1.toDouble()

    val fxColor: Color
        get() = Color(red, green, blue, alpha)

    constructor(color: Color) : this()
    {
        this.red = color.getRed()
        this.green = color.getGreen()
        this.blue = color.getBlue()
        this.alpha = color.getOpacity()
    }

    constructor(red: Double, green: Double, blue: Double, alpha: Double) : this()
    {
        this.red = red
        this.green = green
        this.blue = blue
        this.alpha = alpha
    }

    override fun equals(other: Any?): Boolean
    {
        if (other !is core.Color)
            return false

        return this.alpha == other.alpha &&
                this.red == other.red &&
                this.blue == other.blue &&
                this.green == other.green
    }

    override fun hashCode(): Int
    {
        var result = red.hashCode()
        result = 31 * result + green.hashCode()
        result = 31 * result + blue.hashCode()
        result = 31 * result + alpha.hashCode()
        return result
    }
}