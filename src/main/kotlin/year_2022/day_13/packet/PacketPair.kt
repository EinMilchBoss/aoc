package year_2022.day_13.packet

data class PacketPair(val left: Packet, val right: Packet) {
    fun toPacketDataPairs(): List<PacketDataPair> =
        left.data.values.zip(right.data.values)
            .map(Pair<Packet.Data, Packet.Data>::toPacketDataPair)
}

fun Pair<Packet, Packet>.toPacketPair(): PacketPair =
    PacketPair(first, second)