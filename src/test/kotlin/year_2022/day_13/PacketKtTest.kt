package year_2022.day_13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PacketKtTest {
    @Test
    fun parsePacketPairs() {
        val input = listOf(
            "[1,2,3,4,5]",
            "[10,20,30,40,50]",
            "",
            "[[1],[[]],[2,3],[40,50]]",
            "[[10],[[20]],[3],[400,5]]"
        )
        val expected = listOf(
            Pair(
                Packet(
                    Packet.ValueList(
                        listOf(
                            Packet.Value(1),
                            Packet.Value(2),
                            Packet.Value(3),
                            Packet.Value(4),
                            Packet.Value(5)
                        )
                    )
                ),
                Packet(
                    Packet.ValueList(
                        listOf(
                            Packet.Value(10),
                            Packet.Value(20),
                            Packet.Value(30),
                            Packet.Value(40),
                            Packet.Value(50)
                        )
                    )
                )
            ),
            Pair(
                Packet(
                    Packet.ValueList(
                        listOf(
                            Packet.ValueList(listOf(Packet.Value(1))),
                            Packet.ValueList(listOf(Packet.ValueList(listOf()))),
                            Packet.ValueList(listOf(Packet.Value(2), Packet.Value(3))),
                            Packet.ValueList(listOf(Packet.Value(40), Packet.Value(50))),
                        )
                    )
                ),
                Packet(
                    Packet.ValueList(
                        listOf(
                            Packet.ValueList(listOf(Packet.Value(10))),
                            Packet.ValueList(listOf(Packet.ValueList(listOf(Packet.Value(20))))),
                            Packet.ValueList(listOf(Packet.Value(3))),
                            Packet.ValueList(listOf(Packet.Value(400), Packet.Value(5))),
                        )
                    )
                )
            )
        )

        assertEquals(expected, input.parsePacketPairs())
    }

    @Test
    fun parseValueList() {
        val expected = Packet.ValueList(
            listOf(
                Packet.Value(1),
                Packet.ValueList(
                    listOf(
                        Packet.Value(22),
                        Packet.ValueList(
                            listOf(
                                Packet.Value(333),
                                Packet.Value(4),
                                Packet.Value(55555)
                            )
                        ),
                        Packet.Value(6)
                    )
                ),
                Packet.Value(7777777)
            )
        )

        assertEquals(expected, "[1,[22,[333,4,55555],6],7777777]".parseValueList())
    }

    @Test
    fun `parseValueList only values`() {
        val expected = Packet.ValueList(
            listOf(
                Packet.Value(1),
                Packet.Value(222),
                Packet.Value(3),
                Packet.Value(44),
                Packet.Value(5),
            )
        )

        assertEquals(expected, "[1,222,3,44,5]".parseValueList())
    }

    @Test
    fun `parseValueList inner list`() {
        val expected = Packet.ValueList(
            listOf(
                Packet.Value(1),
                Packet.ValueList(
                    listOf(
                        Packet.Value(2),
                        Packet.Value(3),
                        Packet.Value(4),
                    )
                ),
                Packet.Value(5)
            )
        )

        assertEquals(expected, "[1,[2,3,4],5]".parseValueList())
    }

    @Test
    fun `parseValueList nested lists`() {
        val expected = Packet.ValueList(
            listOf(
                Packet.Value(1),
                Packet.ValueList(
                    listOf(
                        Packet.Value(2),
                        Packet.ValueList(
                            listOf(
                                Packet.Value(3),
                                Packet.Value(4),
                                Packet.Value(5)
                            )
                        )
                    )
                )
            )
        )

        assertEquals(expected, "[1,[2,[3,4,5]]]".parseValueList())
    }

    @Test
    fun `parseValueList with lists of one element`() {
        val expected = Packet.ValueList(
            listOf(
                Packet.Value(1),
                Packet.ValueList(
                    listOf(
                        Packet.Value(22)
                    )
                ),
                Packet.Value(333)
            )
        )

        assertEquals(expected, "[1,[22],333]".parseValueList())
    }

    @Test
    fun `parseValueList with consecutive lists`() {
        val expected = Packet.ValueList(
            listOf(
                Packet.ValueList(
                    listOf(
                        Packet.Value(1)
                    )
                ),
                Packet.ValueList(
                    listOf(
                        Packet.Value(2)
                    )
                )
            )
        )

        assertEquals(expected, "[[1],[2]]".parseValueList())
    }

    @Test
    fun `parseValueList too many opening brackets`() {
        assertThrows<UnevenBracketCountException> { "[1,[2,3,4,5]".parseValueList() }
    }

    @Test
    fun `parseValueList too many closing brackets`() {
        assertThrows<UnevenBracketCountException> { "[1,2,3,4],5]".parseValueList() }
    }

    @Test
    fun `parseValueList outer brackets don't match`() {
        assertThrows<UnevenBracketCountException> { "[1,2,3,4,5".parseValueList() }
        assertThrows<UnevenBracketCountException> { "1,2,3,4,5]".parseValueList() }
    }
}