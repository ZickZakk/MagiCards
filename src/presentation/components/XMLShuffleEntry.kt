package presentation.components

import org.jonnyzzz.kotlin.xml.bind.XAttribute
import org.jonnyzzz.kotlin.xml.bind.jdom.JXML

class XMLShuffleEntry
{
    var source by JXML / XAttribute("source")
    var destination by JXML / XAttribute("destination")
    var flip by JXML / XAttribute("flip")
    var turn by JXML / XAttribute("turn")
}