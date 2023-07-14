package year_2022.day_13.packet.pair

import year_2022.day_13.packet.Packet

fun String.parsePacketPairs(): List<PacketPair> =
    split(getEmptyLineSeparator()).map { packetPairContent ->
        packetPairContent.lines()
            .map(String::toPacket)
            .toPair()
            .toPacketPair()
    }

private fun List<Packet>.toPair(): Pair<Packet, Packet> =
    zipWithNext().first()

private fun getEmptyLineSeparator(): String =
    List(2) { System.lineSeparator() }.joinToString("")