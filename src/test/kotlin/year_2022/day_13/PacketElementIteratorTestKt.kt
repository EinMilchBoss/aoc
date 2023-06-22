package year_2022.day_13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PacketElementIteratorTestKt {
    private fun PacketElementIterator.collect(): List<String> =
        asSequence().map(PacketElement::get).toList()

    @Test
    fun `packetElementIterator only value elements`() {
        val expected = listOf("1", "22", "333")

        val actual = "[1,22,333]".packetElementIterator().collect()

        assertEquals(expected, actual)
    }

    @Test
    fun `packetElementIterator with list elements`() {
        val expected = listOf("1", "[22,333]", "4444")

        val actual = "[1,[22,333],4444]".packetElementIterator().collect()

        assertEquals(expected, actual)
    }
}