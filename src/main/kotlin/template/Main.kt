package template

import utils.test
import java.io.File

fun solveFirst(input: List<String>): String {
    return ""
}

fun solveSecond(input: List<String>): String {
    return ""
}

fun main() {
    val pathPrefix = "./src/main/kotlin/[...]"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "[...]", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "[...]", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}