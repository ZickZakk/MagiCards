package io

import core.shuffeling.CustomShuffleType
import core.shuffeling.ShuffleEntry
import core.shuffeling.ShuffleType
import org.jonnyzzz.kotlin.xml.bind.XAnyElements
import org.jonnyzzz.kotlin.xml.bind.XAttribute
import org.jonnyzzz.kotlin.xml.bind.XSub
import org.jonnyzzz.kotlin.xml.bind.jdom.JXML

class XMLShuffle
{
    var name by JXML / XAttribute("name")
    var deckSize by JXML / XAttribute("deckSize")
    var entries by JXML / "XMLShuffleEntries" / XAnyElements / XSub(XMLShuffleEntry::class.java)

    fun isValid() : Boolean
    {
        try
        {
            if(name.isNullOrBlank())
                return false

            if(entries!!.size != deckSize!!.toInt())
                return false

            val sourceList = mutableListOf<Int>()
            val destinationList = mutableListOf<Int>()

            for (i in 0 until deckSize!!.toInt())
            {
                sourceList.add(i)
                destinationList.add(i)
            }

            for (entry in entries!!)
            {
                sourceList.remove(entry.source!!.toInt())
                destinationList.remove(entry.destination!!.toInt())

                if (entry.flip!! != "true" && entry.flip!! != "false")
                    return false

                if (entry.turn!! != "true" && entry.turn!! != "false")
                    return false
            }

            if(sourceList.isNotEmpty())
                return false

            if(destinationList.isNotEmpty())
                return false
        }
        catch (e : Exception)
        {
            return false
        }

        return true
    }

    fun toShuffleType() : ShuffleType
    {
        return CustomShuffleType(name!!, entries!!.map { ShuffleEntry(it.source!!.toInt(), it.destination!!.toInt(), it.flip!!.toBoolean(), it.turn!!.toBoolean()) })
    }
}

