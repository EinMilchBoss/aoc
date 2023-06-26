package year_2022.day_13.packet

data class Packet(val data: List) {
    sealed class Data
    data class Value(val value: Int) : Data()
    data class List(val values: kotlin.collections.List<Data>) : Data()
}