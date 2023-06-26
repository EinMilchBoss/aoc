package year_2022.day_13

import utils.test
import year_2022.day_13.packet.parsePacketPairs
import java.io.File

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