package year_2022.day_01

import utils.test
import java.io.File

fun List<String>.splitBlockSums() =
    this.joinToString("\n")
        .split("\n\n")
        .map { it.split("\n").map(String::toInt).sum() }

fun solveFirst(input: List<String>): String =
    input.splitBlockSums()
        .max()
        .toString()

fun solveSecond(input: List<String>): String =
    input.splitBlockSums()
        .sortedByDescending { it }
        .take(3)
        .sum()
        .toString()

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_01"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "24000", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "45000", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}