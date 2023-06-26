package year_2022.day_13.packet

fun String.parsePacketPairs(): List<Pair<Packet, Packet>> {
    return split(getEmptyLineSeparator()).map { packetPairContent ->
        packetPairContent.lines()
            .map { Packet(it.parseValueList()) }
            .zipWithNext()
            .first()
    }
}

private fun getEmptyLineSeparator(): String =
    List(2) { System.lineSeparator() }.joinToString("")

private fun String.parseValueList(): Packet.List {
    val values = mutableListOf<Packet.Data>()
    for (packetElement in packetElementIterator()) {
        val content = packetElement.content
        when (packetElement) {
            is PacketElement.Value -> values.add(content.tryParsePacketValue())
            is PacketElement.List -> values.add(content.parseValueList())
        }
    }
    return Packet.List(values)
}

private fun String.tryParsePacketValue(): Packet.Value =
    try {
        Packet.Value(toInt())
    } catch (cause: NumberFormatException) {
        throw UnevenBracketCountException(cause)
    }