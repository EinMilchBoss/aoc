package aoc_2020_02

import test
import java.io.File

data class PasswordEntry(val first: Int, val second: Int, val char: Char, val password: String)

fun String.toPasswordEntry(): PasswordEntry = this.split(' ').let { lineParts ->
    val (min, max) = lineParts[0].split('-').map(String::toInt)
    val char = lineParts[1].first()
    val password = lineParts[2]

    PasswordEntry(min, max, char, password)
}

fun solveFirst(input: List<String>): String = input.map(String::toPasswordEntry).count { entry ->
    entry.password.count { it == entry.char }.let { charCount ->
        charCount >= entry.first && charCount <= entry.second
    }
}.toString()

fun solveSecond(input: List<String>): String = input.map(String::toPasswordEntry).count { entry ->
    listOf(
        entry.password[entry.first - 1], entry.password[entry.second - 1]
    ).count { it == entry.char } == 1
}.toString()

fun main() {
    val pathPrefix = "./src/main/kotlin/aoc_2020_02"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "2", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "1", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}