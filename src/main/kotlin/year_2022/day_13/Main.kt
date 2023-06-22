package year_2022.day_13

import utils.test
import java.io.File

fun solveFirst(input: List<String>): String {
    val x = input.parsePacketPairs()
    println(x)
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