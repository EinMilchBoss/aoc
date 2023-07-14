package year_2022.day_13.packet

import year_2022.day_13.packet.pair.toPacket
import year_2022.day_13.toPair

private val dividerPackets = listOf(
    "[[2]]".toPacket(), "[[6]]".toPacket()
)

fun List<String>.findDecoderKey() =
    allPacketsSorted().multiplyPositionsOfDividerPackets()

private fun List<String>.allPacketsSorted(): List<Packet> =
    parseReceivedPackets().addDividerPackets()
        .sorted()

private fun List<String>.parseReceivedPackets(): List<Packet> =
    filter(String::isNotEmpty).map(String::toPacket)

private fun List<Packet>.addDividerPackets(): List<Packet> =
    this + dividerPackets

private fun List<Packet>.multiplyPositionsOfDividerPackets(): Int =
    findPositionsOfDividerPackets()
        .multiply()

private fun List<Packet>.findPositionsOfDividerPackets(): Pair<Int, Int> =
    addPositionsToPackets().findDividerPackets()
        .positionsOfDividerPackets()

private fun List<Packet>.addPositionsToPackets(): List<Pair<Int, Packet>> =
    mapIndexed { index, packet -> index + 1 to packet }

private fun List<Pair<Int, Packet>>.findDividerPackets(): List<Pair<Int, Packet>> =
    filter { (_, packet) -> packet in dividerPackets }

private fun List<Pair<Int, Packet>>.positionsOfDividerPackets(): Pair<Int, Int> =
    map { (position, _) -> position }
        .toPair()

private fun Pair<Int, Int>.multiply(): Int =
    first * second