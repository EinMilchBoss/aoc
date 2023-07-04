package year_2022.day_13.packet

sealed class PacketElement(val content: String) {
    class List(content: String) : PacketElement(content)
    class Value(content: String) : PacketElement(content)
}