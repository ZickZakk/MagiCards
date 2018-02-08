package xmlTest

import io.XMLShuffle
import org.jonnyzzz.kotlin.xml.bind.jdom.JDOM
import org.junit.Test
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import org.jdom2.input.DOMBuilder
import kotlin.test.assertEquals


class XMLTests
{
    @Test
    fun testLoadXML()
    {
        val classLoader = javaClass.classLoader

        val file = File(classLoader.getResource("testShuffle.xml")!!.file)

        val xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)

        val document = DOMBuilder().build(xmlDoc)

        val read = JDOM.load(document.rootElement, XMLShuffle::class.java)

        assertEquals("TestShuffle", read.name)
        assertEquals("3", read.deckSize)
        assertEquals(3, read.entries!!.size)

        assertEquals("0", read.entries!![0].source)
        assertEquals("2", read.entries!![0].destination)
        assertEquals("false", read.entries!![0].flip)
        assertEquals("false", read.entries!![0].turn)

        assertEquals("1", read.entries!![1].source)
        assertEquals("1", read.entries!![1].destination)
        assertEquals("true", read.entries!![1].flip)
        assertEquals("false", read.entries!![1].turn)

        assertEquals("2", read.entries!![2].source)
        assertEquals("0", read.entries!![2].destination)
        assertEquals("false", read.entries!![2].flip)
        assertEquals("true", read.entries!![2].turn)
    }
}