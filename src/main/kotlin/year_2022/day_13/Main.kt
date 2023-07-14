package year_2022.day_13

import utils.test
import year_2022.day_13.packet.findDecoderKey
import year_2022.day_13.packet.pair.parsePacketPairs
import year_2022.day_13.packet.sumIndicesOfPairsInOrder
import java.io.File

fun solveFirst(input: List<String>): String =
    input.joinToString("\n")
        .parsePacketPairs()
        .sumIndicesOfPairsInOrder()
        .toString()

fun solveSecond(input: List<String>): String =
    input.findDecoderKey()
        .toString()

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_13"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "13", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "140", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}