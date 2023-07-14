package year_2022.day_13

import utils.test
import year_2022.day_13.packet.pair.*
import year_2022.day_13.packet.sumIndicesOfPairsInOrder
import java.io.File

fun solveFirst(input: List<String>): String =
    input.joinToString("\n")
        .parsePacketPairs()
        .sumIndicesOfPairsInOrder()
        .toString()

fun solveSecond(input: List<String>): String {
    val receivedPackets =
        input.filter { line -> line != "" }
            .map(String::toPacket)

    val dividerPackets = listOf(
        "[[2]]".toPacket(),
        "[[6]]".toPacket()
    )

    val allPackets = receivedPackets + dividerPackets

    val sortedPackets = allPackets.sortedWith { left, right ->
        if (PacketPair(left, right).isInOrder()) -1
        else 1
    }

    val (first, last) = sortedPackets.mapIndexed { index, packet -> index to packet }
        .filter { (_, packet) -> packet in dividerPackets }
        .map { (index, _) -> index + 1 }

    return (first * last).toString()
}

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_13"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "13", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "140", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}