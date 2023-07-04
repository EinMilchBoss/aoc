package year_2022.day_13

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import year_2022.day_13.packet.Packet
import year_2022.day_13.packet.PacketPair
import year_2022.day_13.packet.PacketPairComparator

internal class MainKtTest {
    private fun PacketPair.computeActual() =
        PacketPairComparator(this).isInOrder()

    @Test
    fun isInOrder() {
        val packetPair = PacketPair(
            Packet(
                Packet.List(
                    listOf(
                        Packet.Value(1),
                        Packet.List(
                            listOf(
                                Packet.Value(2),
                                Packet.List(
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
            ), Packet(
                Packet.List(
                    listOf(
                        Packet.Value(1),
                        Packet.List(
                            listOf(
                                Packet.Value(2),
                                Packet.List(
                                    listOf(
                                        Packet.Value(3),
                                        Packet.Value(4),
                                        Packet.List(listOf(Packet.Value(6)))
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )

        val actual = packetPair.computeActual()

        assert(actual)
    }

    @Test
    fun `isInOrder only packet values`() {
        val packetPair = PacketPair(
            Packet(
                Packet.List(
                    listOf(
                        Packet.Value(1), Packet.Value(1), Packet.Value(3)
                    )
                )
            ), Packet(
                Packet.List(
                    listOf(
                        Packet.Value(1), Packet.Value(1), Packet.Value(5)
                    )
                )
            )
        )

        val actual = packetPair.computeActual()

        assert(actual)
    }

    @Test
    fun `isInOrder with packet lists`() {
        val packetPair = PacketPair(
            Packet(
                Packet.List(
                    listOf(
                        Packet.List(listOf(Packet.Value(5))),
                        Packet.List(listOf(Packet.Value(5))),
                        Packet.List(listOf(Packet.Value(20)))
                    )
                )
            ), Packet(
                Packet.List(
                    listOf(
                        Packet.List(listOf(Packet.Value(5))),
                        Packet.List(listOf(Packet.Value(5))),
                        Packet.List(listOf(Packet.Value(5)))
                    )
                )
            )
        )

        val actual = packetPair.computeActual()

        assert(!actual)
    }

    @Test
    fun `isInOrder returns after first difference`() {
        val packetPair = PacketPair(
            Packet(
                Packet.List(
                    listOf(
                        Packet.Value(1), Packet.Value(1), Packet.Value(1)
                    )
                )
            ), Packet(
                Packet.List(
                    listOf(
                        Packet.Value(5), Packet.Value(1), Packet.Value(1)
                    )
                )
            )
        )

        val actual = packetPair.computeActual()

        assert(actual)
    }

    @Test
    fun `isInOrder converts values into a list if necessary`() {
        val packetPair = PacketPair(
            Packet(
                Packet.List(
                    listOf(
                        Packet.Value(5), Packet.List(listOf(Packet.Value(1))), Packet.Value(1)
                    )
                )
            ), Packet(
                Packet.List(
                    listOf(
                        Packet.List(listOf(Packet.Value(5))), Packet.Value(1), Packet.Value(20)
                    )
                )
            )
        )

        val actual = packetPair.computeActual()

        assert(actual)
    }

    @Test
    fun `isInOrder takes the length of the packets into account`() {
        val packetPair = PacketPair(
            Packet(
                Packet.List(
                    listOf(
                        Packet.Value(5), Packet.Value(5)
                    )
                )
            ), Packet(
                Packet.List(
                    listOf(
                        Packet.Value(5), Packet.Value(5), Packet.Value(20)
                    )
                )
            )
        )

        val actual = packetPair.computeActual()

        assert(actual)
    }

    @Test
    fun `isInOrder nested empty lists`() {
        val packetPair = PacketPair(
            Packet(
                Packet.List(
                    listOf(
                        Packet.List(
                            listOf(
                                Packet.List(listOf())
                            )
                        )
                    )
                )
            ), Packet(
                Packet.List(
                    listOf(
                        Packet.List(listOf())
                    )
                )
            )
        )

        val actual = packetPair.computeActual()

        assert(!actual)
    }

    @Test
    fun `isInOrder single value to empty list`() {
        val packetPair = PacketPair(
            Packet(
                Packet.List(
                    listOf(
                        Packet.Value(1)
                    )
                )
            ), Packet(
                Packet.List(listOf())
            )
        )

        val actual = packetPair.computeActual()

        assert(!actual)
    }

    @Test
    fun `isInOrder throws if packets are identical`() {
        val packetPair = PacketPair(
            Packet(
                Packet.List(
                    listOf(
                        Packet.Value(5), Packet.List(listOf(Packet.Value(1))), Packet.Value(1)
                    )
                )
            ), Packet(
                Packet.List(
                    listOf(
                        Packet.List(listOf(Packet.Value(5))), Packet.Value(1), Packet.Value(1)
                    )
                )
            )
        )

        assertThrows<IdenticalPacketsException> { packetPair.computeActual() }
    }
}