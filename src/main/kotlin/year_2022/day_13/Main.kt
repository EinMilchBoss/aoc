package year_2022.day_13

import utils.aoc.*
import year_2022.day_13.packet.findDecoderKey
import year_2022.day_13.packet.pair.parsePacketPairs
import year_2022.day_13.packet.sumIndicesOfPairsInOrder

fun String.partOne(): String =
    lines().joinToString("\n")
        .parsePacketPairs()
        .sumIndicesOfPairsInOrder()
        .toString()

fun String.partTwo(): String =
    lines().findDecoderKey()
        .toString()

fun main() {
    val inputs = Inputs(Exercise(2022, 13))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("13"))
    println(two.testProtocol("140"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}