package year_2022.day_13.packet

import year_2022.day_13.packet.pair.PacketPair
import year_2022.day_13.packet.pair.isInOrder

fun List<PacketPair>.sumIndicesOfPairsInOrder(): Int =
    assignIndices()
        .filter(Pair<Int, PacketPair>::isPacketPairInOrder)
        .sumIndices()

private fun List<PacketPair>.assignIndices(): List<Pair<Int, PacketPair>> =
    mapIndexed { index, packetPair -> index + 1 to packetPair }

private fun Pair<Int, PacketPair>.isPacketPairInOrder(): Boolean =
    let { (_, packetPair) -> packetPair.isInOrder() }

private fun List<Pair<Int, PacketPair>>.sumIndices(): Int =
    sumOf { (index, _) -> index }