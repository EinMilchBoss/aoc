package year_2022.day_13

import utils.test
import year_2022.day_13.packet.PacketPairComparator
import year_2022.day_13.packet.parsePacketPairs
import java.io.File

class IdenticalPacketsException : Exception() {
    override val message = "Packets are identical although they have to be different in order to compare them."
}

fun solveFirst(input: List<String>): String {
    val comparator = input.joinToString("\n")
        .parsePacketPairs()
        .mapIndexed { index, packetPair -> index + 1 to packetPair }
        .filter { (_, packetPair) -> PacketPairComparator(packetPair).isInOrder() }
        .sumOf { (index, _) -> index }

    return comparator.toString()
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