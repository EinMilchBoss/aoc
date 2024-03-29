package year_2022.day_13.packet.pair

import year_2022.day_13.packet.Packet
import year_2022.day_13.packet.element.PacketElement
import year_2022.day_13.packet.element.packetElementIterator
import year_2022.day_13.packet.exception.UnevenBracketCountException

fun String.toPacket(): Packet =
    Packet(parseValueList())

private fun String.parseValueList(): Packet.List =
    packetElementIterator().asSequence()
        .fold(Packet.List(emptyList())) { acc, packetElement ->
            val (content) = packetElement
            when (packetElement) {
                is PacketElement.Value -> acc + content.tryParsePacketValue()
                is PacketElement.List -> acc + content.parseValueList()
            }
        }

private fun String.tryParsePacketValue(): Packet.Value =
    try {
        Packet.Value(toInt())
    } catch (cause: NumberFormatException) {
        throw UnevenBracketCountException(cause)
    }