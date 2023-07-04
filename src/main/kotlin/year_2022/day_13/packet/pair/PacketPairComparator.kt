package year_2022.day_13.packet.pair

import year_2022.day_13.packet.Packet
import year_2022.day_13.packet.exception.IdenticalPacketsException

fun PacketPair.isInOrder(): Boolean {
    for (packetDataPair in toPacketDataPairs()) {
        if (packetDataPair.areBothValues()) {
            val (leftValue, rightValue) = packetDataPair.unwrapPacketDataPairValues()
            return when {
                leftValue < rightValue -> true
                leftValue > rightValue -> false
                else -> continue
            }
        }

        if (packetDataPair.areBothLists()) {
            try {
                return packetDataPair.toPacketPair()
                    .isInOrder()
            } catch (_: IdenticalPacketsException) {
                continue
            }
        }

        val (left, right) = packetDataPair
        if (left is Packet.Value && right is Packet.List) {
            val newLeft = left.toPacketList()
            try {
                return PacketPair(Packet(newLeft), Packet(right)).isInOrder()
            } catch (_: IdenticalPacketsException) {
                continue
            }
        }
        if (left is Packet.List && right is Packet.Value) {
            val newRight = right.toPacketList()
            try {
                return PacketPair(Packet(left), Packet(newRight)).isInOrder()
            } catch (_: IdenticalPacketsException) {
                continue
            }
        }
    }

    return when {
        left.data.values.size < right.data.values.size -> true
        left.data.values.size > right.data.values.size -> false
        else -> throw IdenticalPacketsException()
    }
}

private fun Packet.DataPair.unwrapPacketDataPairValues(): Pair<Int, Int> =
    Pair((left as Packet.Value).value, (right as Packet.Value).value)

private fun Packet.DataPair.toPacketPair(): PacketPair =
    PacketPair(Packet(left as Packet.List), Packet(right as Packet.List))

private fun Packet.Value.toPacketList(): Packet.List =
    Packet.List(listOf(this))
