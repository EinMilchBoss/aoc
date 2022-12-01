package aoc_2022_01

import lineSeparatedBlocks
import test
import java.io.File

fun solveFirst(input: List<String>): String {
    val elfCalories = input.lineSeparatedBlocks { it == "" }.map { it.map(String::toInt) }
    return elfCalories.maxOfOrNull { it.sum() }.toString()
}

fun solveSecond(input: List<String>): String {
    val elfCalories = input.lineSeparatedBlocks { it == "" }.map { it.map(String::toInt) }
    return elfCalories.map { it.sum() }.sortedByDescending { it }.take(3).sum().toString()
}

fun main() {
    val pathPrefix = "./src/main/kotlin/aoc_2022_01"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "24000", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "45000", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}