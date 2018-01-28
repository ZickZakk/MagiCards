package presentation

import core.shuffeling.FaroShuffleType
import core.shuffeling.ShuffleRegister
import presentation.views.MainView
import tornadofx.*

class Main : App(MainView::class)
{
    init
    {
        ShuffleRegister.addShuffleType(FaroShuffleType())
    }
}
