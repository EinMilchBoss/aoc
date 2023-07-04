package year_2022.day_13.packet

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import year_2022.day_13.packet.element.PacketElementIterator
import year_2022.day_13.packet.element.packetElementIterator

internal class PacketElementIteratorKtTest {
    private fun String.getIteratorValues(): List<String> =
        packetElementIterator().collect()

    private fun PacketElementIterator.collect(): List<String> =
        asSequence().map { it.content }
            .toList()

    @Test
    fun `packetElementIterator only value elements`() {
        val expected = listOf("1", "22", "333")

        val actual = "[1,22,333]".getIteratorValues()

        assertEquals(expected, actual)
    }

    @Test
    fun `packetElementIterator with list elements`() {
        val expected = listOf("1", "[22,333]", "4444")

        val actual = "[1,[22,333],4444]".getIteratorValues()

        assertEquals(expected, actual)
    }
}