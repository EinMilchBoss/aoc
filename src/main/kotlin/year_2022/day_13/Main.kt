package year_2022.day_13

import utils.test
import year_2022.day_13.packet.Packet
import year_2022.day_13.packet.PacketPair
import year_2022.day_13.packet.parsePacketPairs
import java.io.File

class IdenticalPacketsException : Exception() {
    override val message = "Packets are identical although they have to be different in order to compare them."
}

fun PacketPair.isInRightOrder(): Boolean {
    val (leftPacket, rightPacket) = this
    for ((left, right) in leftPacket.data.values.zip(rightPacket.data.values)) {
        // rule 1
        if (left is Packet.Value && right is Packet.Value) {
            if (left.value < right.value) {
                return true
            } else if (left.value > right.value) {
                return false
            }
        }

        // rule 2
        if (left is Packet.List && right is Packet.List) {
            try {
                return PacketPair(Packet(left), Packet(right)).isInRightOrder()
            } catch (_: IdenticalPacketsException) {
                continue
            }
        }

        // rule 3
        if (left is Packet.Value && right is Packet.List) {
            val newLeft = left.toPacketList()
            try {
                return PacketPair(Packet(newLeft), Packet(right)).isInRightOrder()
            } catch (_: IdenticalPacketsException) {
                continue
            }
        }
        if (left is Packet.List && right is Packet.Value) {
            val newRight = right.toPacketList()
            try {
                return PacketPair(Packet(left), Packet(newRight)).isInRightOrder()
            } catch (_: IdenticalPacketsException) {
                continue
            }
        }
    }

    return when {
        leftPacket.data.values.size < rightPacket.data.values.size -> true
        leftPacket.data.values.size > rightPacket.data.values.size -> false
        else -> throw IdenticalPacketsException()
    }
}

fun Packet.Value.toPacketList(): Packet.List =
    Packet.List(listOf(this))


//fun <L, R> List<L>.zipAll(other: List<R>, leftDefault: L, rightDefault: R): List<Pair<L, R>> {
//    if (this.size < other.size) {
//        val this.fillToLength(other.size, leftDefault)
//    } else {
//        val other = other.fillToLength(this.size, rightDefault)
//    }
//}
//
//fun <T> List<T>.fillToLength(length: Int, fillValue: T): List<T> =
//    if (length == 0) this
//    else this + fillValue + fillToLength(length - 1, fillValue)

fun solveFirst(input: List<String>): String {
    val x =
        input.joinToString("\n")
            .parsePacketPairs()
    println(x)

    // #1) if ints: compare ints (left < right is expected)
    // #2) if lists:
    // compare first value, then second, etc. (see first rule)
    // left list length < right list length is expected
    // #3) if int to list / list to int:
    // convert int to list
    // see #2)


    return ""
}

fun solveSecond(input: List<String>): String {
    return ""
}

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_13"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "13", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}