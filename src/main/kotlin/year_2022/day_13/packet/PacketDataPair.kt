package year_2022.day_13.packet

data class PacketDataPair(val left: Packet.Data, val right: Packet.Data) {
    fun areBothLists(): Boolean =
        left is Packet.List && right is Packet.List

    fun areBothValues(): Boolean =
        left is Packet.Value && right is Packet.Value
}

fun Pair<Packet.Data, Packet.Data>.toPacketDataPair(): PacketDataPair =
    PacketDataPair(first, second)