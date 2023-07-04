package year_2022.day_13.packet.pair

import year_2022.day_13.packet.Packet
import year_2022.day_13.packet.exception.IdenticalPacketsException
import year_2022.day_13.packet.toPacketDataPair

fun PacketPair.isInOrder(): Boolean =
    compareNextPacketDataPair(toPacketDataPairs(), 0)

private fun PacketPair.compareNextPacketDataPair(
    packetDataPairs: List<Packet.DataPair>,
    index: Int,
): Boolean =
    try {
        tryToComparePacketDataPair(packetDataPairs, index)
    } catch (_: IdenticalPacketsException) {
        tryIfLeftPacketIsSmaller()
    }

private fun PacketPair.tryToComparePacketDataPair(
    packetDataPairs: List<Packet.DataPair>,
    index: Int,
): Boolean =
    packetDataPairs.getOrElse(index) { throw IdenticalPacketsException() }
        .run {
            when {
                areBothValues() -> compareValues(packetDataPairs, index)
                areBothLists() -> iterateThroughLists(packetDataPairs, index)
                else -> convertAndIterateThroughLists(packetDataPairs, index)
            }
        }

private fun PacketPair.tryIfLeftPacketIsSmaller(): Boolean {
    val leftSize = left.data.values.size
    val rightSize = right.data.values.size
    return when {
        leftSize < rightSize -> true
        leftSize > rightSize -> false
        else -> throw IdenticalPacketsException()
    }
}

private fun PacketPair.compareValues(packetDataPairs: List<Packet.DataPair>, index: Int): Boolean =
    packetDataPairs[index].tryUnwrapPacketValues()
        .let { (leftValue, rightValue) ->
            when {
                leftValue < rightValue -> true
                leftValue > rightValue -> false
                else -> compareNextPacketDataPair(packetDataPairs, index + 1)
            }
        }

private fun PacketPair.iterateThroughLists(packetDataPairs: List<Packet.DataPair>, index: Int): Boolean =
    iterateThroughListsSafely(packetDataPairs, index) {
        tryIterateThroughLists(packetDataPairs, index)
    }

private fun PacketPair.convertAndIterateThroughLists(
    packetDataPairs: List<Packet.DataPair>,
    index: Int,
): Boolean =
    iterateThroughListsSafely(packetDataPairs, index) {
        tryConvertAndIterateThroughLists(packetDataPairs, index)
    }

private fun PacketPair.iterateThroughListsSafely(
    packetDataPairs: List<Packet.DataPair>,
    index: Int,
    operation: () -> Boolean,
): Boolean =
    try {
        operation()
    } catch (_: IdenticalPacketsException) {
        compareNextPacketDataPair(packetDataPairs, index + 1)
    }

private fun tryIterateThroughLists(
    packetDataPairs: List<Packet.DataPair>,
    index: Int,
): Boolean =
    packetDataPairs[index].toPacketPair()
        .run {
            compareNextPacketDataPair(toPacketDataPairs(), 0)
        }

private fun tryConvertAndIterateThroughLists(
    packetDataPairs: List<Packet.DataPair>,
    index: Int,
): Boolean =
    packetDataPairs[index].toPacketListPair()
        .toPacketDataPair()
        .toPacketPair()
        .run {
            compareNextPacketDataPair(toPacketDataPairs(), 0)
        }