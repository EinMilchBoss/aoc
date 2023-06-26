package year_2022.day_13.packet

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PacketKtTest {
    companion object {
        private val INPUT = listOf(
            "[1,2,3,4,5]",
            "[10,20,30,40,50]",
            "",
            "[[1],[[]],[2,3],[40,50]]",
            "[[10],[[20]],[3],[400,5]]"
        ).joinToString("\n")
    }

    @Test
    fun parsePacketPairs() {
        val expected = listOf(
            Pair(
                Packet(
                    Packet.List(
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
                    Packet.List(
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
                    Packet.List(
                        listOf(
                            Packet.List(listOf(Packet.Value(1))),
                            Packet.List(listOf(Packet.List(listOf()))),
                            Packet.List(listOf(Packet.Value(2), Packet.Value(3))),
                            Packet.List(listOf(Packet.Value(40), Packet.Value(50))),
                        )
                    )
                ),
                Packet(
                    Packet.List(
                        listOf(
                            Packet.List(listOf(Packet.Value(10))),
                            Packet.List(listOf(Packet.List(listOf(Packet.Value(20))))),
                            Packet.List(listOf(Packet.Value(3))),
                            Packet.List(listOf(Packet.Value(400), Packet.Value(5))),
                        )
                    )
                )
            )
        )

        val actual = INPUT.parsePacketPairs()

        assertEquals(expected, actual)
    }
}