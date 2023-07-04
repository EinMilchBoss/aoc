package year_2022.day_13.packet

data class Packet(val data: List) {
    sealed class Data
    data class Value(val value: Int) : Data()
    data class List(val values: kotlin.collections.List<Data>) : Data() {
        operator fun plus(value: Value): List =
            List(values + value)

        operator fun plus(values: List): List =
            List(this.values + values)
    }

    data class DataPair(val left: Data, val right: Data) {
        fun areBothLists(): Boolean =
            left is List && right is List

        fun areBothValues(): Boolean =
            left is Value && right is Value
    }
}

fun Pair<Packet.Data, Packet.Data>.toPacketDataPair(): Packet.DataPair =
    Packet.DataPair(first, second)