package year_2022.day_13.packet

import year_2022.day_13.IdenticalPacketsException

class PacketPairComparator(private val packetPair: PacketPair) {
    fun isInOrder(): Boolean =
        packetPair.isPacketPairInOrder()

    private fun PacketPair.isPacketPairInOrder(): Boolean {
        for (packetDataPair in toPacketDataPairs()) { // can be removed to function with recursion (and index as param)
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
                        .isPacketPairInOrder()
                } catch (_: IdenticalPacketsException) {
                    continue
                }
            }

            val (left, right) = packetDataPair
            if (left is Packet.Value && right is Packet.List) {
                val newLeft = left.toPacketList()
                try {
                    return PacketPair(Packet(newLeft), Packet(right)).isPacketPairInOrder()
                } catch (_: IdenticalPacketsException) {
                    continue
                }
            }
            if (left is Packet.List && right is Packet.Value) {
                val newRight = right.toPacketList()
                try {
                    return PacketPair(Packet(left), Packet(newRight)).isPacketPairInOrder()
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
}

private fun PacketDataPair.unwrapPacketDataPairValues(): Pair<Int, Int> =
    Pair((left as Packet.Value).value, (right as Packet.Value).value)

private fun PacketDataPair.toPacketPair(): PacketPair =
    PacketPair(Packet(left as Packet.List), Packet(right as Packet.List))

private fun Packet.Value.toPacketList(): Packet.List =
    Packet.List(listOf(this))
