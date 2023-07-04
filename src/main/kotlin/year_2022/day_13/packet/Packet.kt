package year_2022.day_13.packet

import year_2022.day_13.packet.pair.PacketPair

data class Packet(val data: List) {
    sealed class Data {
        fun toPacketListIfValue(): List =
            when (this) {
                is Value -> toPacketList()
                is List -> this
            }
    }

    data class Value(val value: Int) : Data() {
        fun toPacketList(): List =
            List(listOf(this))
    }

    data class List(val values: kotlin.collections.List<Data>) : Data() {
        operator fun plus(value: Value): List =
            List(values + value)

        operator fun plus(values: List): List =
            List(this.values + values)
    }

    data class DataPair(val left: Data, val right: Data) {
        fun toPacketPair(): PacketPair =
            PacketPair(Packet(left as List), Packet(right as List))

        fun toPacketListPair(): Pair<List, List> =
            Pair(left.toPacketListIfValue(), right.toPacketListIfValue())

        fun areBothLists(): Boolean =
            left is List && right is List

        fun areBothValues(): Boolean =
            left is Value && right is Value

        fun tryUnwrapPacketValues(): Pair<Int, Int> =
            Pair((left as Value).value, (right as Value).value)
    }
}

fun Pair<Packet.Data, Packet.Data>.toPacketDataPair(): Packet.DataPair =
    Packet.DataPair(first, second)