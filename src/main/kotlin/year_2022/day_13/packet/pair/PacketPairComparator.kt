package year_2022.day_13.packet.pair

import year_2022.day_13.packet.Packet
import year_2022.day_13.packet.exception.IdenticalPacketsException

fun iterateAreBothValues(packetPair: PacketPair, packetDataPairs: List<Packet.DataPair>, index: Int): Boolean =
    packetDataPairs[index].unwrapPacketDataPairValues()
        .let { (leftValue, rightValue) ->
            when {
                leftValue < rightValue -> true
                leftValue > rightValue -> false
                else -> iterate(packetPair, packetDataPairs, index + 1)
            }
        }

fun iterateAreBothLists(packetPair: PacketPair, packetDataPairs: List<Packet.DataPair>, index: Int): Boolean =
    try {
        val newPacketPair = packetDataPairs[index].toPacketPair()
        iterate(
            newPacketPair, newPacketPair
                .toPacketDataPairs(), 0
        )
    } catch (_: IdenticalPacketsException) {
        try {
            iterate(packetPair, packetDataPairs, index + 1)
        } catch (_: IdenticalPacketsException) {
            packetPair
                .isLeftPacketSmaller()
        }
    }

fun iterateOneOfEach(packetPair: PacketPair, packetDataPairs: List<Packet.DataPair>, index: Int): Boolean {
    val (left, right) = packetDataPairs[index]
    val (newLeft, newRight) = listOf(left, right).map(Packet.Data::toPacketListIfValue)
    return try {
        val newPacketPair = PacketPair(Packet(newLeft), Packet(newRight))
        iterate(newPacketPair, newPacketPair.toPacketDataPairs(), 0)
    } catch (_: IdenticalPacketsException) {
        try {
            iterate(packetPair, packetDataPairs, index + 1)
        } catch (_: IdenticalPacketsException) {
            packetPair
                .isLeftPacketSmaller()
        }
    }
}

fun iterate(packetPair: PacketPair, packetDataPairs: List<Packet.DataPair>, index: Int): Boolean {
    return try {
        val packetDataPair = packetDataPairs.getOrElse(index) { throw IdenticalPacketsException() }
        when {
            packetDataPair.areBothValues() -> iterateAreBothValues(packetPair, packetDataPairs, index)
            packetDataPair.areBothLists() -> iterateAreBothLists(packetPair, packetDataPairs, index)
            else -> iterateOneOfEach(packetPair, packetDataPairs, index)
        }
    } catch (_: IdenticalPacketsException) {
        packetPair.isLeftPacketSmaller()
    }
}

fun PacketPair.isInOrder(): Boolean {
    return iterate(this, toPacketDataPairs(), 0)
}

private fun PacketPair.isLeftPacketSmaller(): Boolean {
    val leftSize = left.data.values.size
    val rightSize = right.data.values.size
    return when {
        leftSize < rightSize -> true
        leftSize > rightSize -> false
        else -> throw IdenticalPacketsException()
    }
}

private fun Packet.DataPair.unwrapPacketDataPairValues(): Pair<Int, Int> =
    Pair((left as Packet.Value).value, (right as Packet.Value).value)

private fun Packet.DataPair.toPacketPair(): PacketPair =
    PacketPair(Packet(left as Packet.List), Packet(right as Packet.List))

private fun Packet.Data.toPacketListIfValue(): Packet.List =
    when (this) {
        is Packet.Value -> this.toPacketList()
        is Packet.List -> this
    }

private fun Packet.Value.toPacketList(): Packet.List =
    Packet.List(listOf(this))
