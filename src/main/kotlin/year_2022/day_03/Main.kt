package year_2022.day_03

import utils.test
import java.io.File

fun Char.priority(): Int =
    if (this in 'a'..'z') this - 'a' + 1
    else this - 'A' + 27

fun String.splitInHalf(): Pair<String, String> =
    (length / 2).let { halfSize ->
        take(halfSize) to takeLast(halfSize)
    }

fun solveFirst(input: List<String>): String =
    input.map(String::splitInHalf)
        .map { (compartmentA, compartmentB) ->
            compartmentA.first { it in compartmentB }
        }
        .sumOf(Char::priority)
        .toString()

fun solveSecond(input: List<String>): String =
    input.chunked(3)
        .map { (rucksackA, rucksackB, rucksackC) ->
            rucksackA.first { it in rucksackB && it in rucksackC }
        }
        .sumOf(Char::priority)
        .toString()

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_03"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "157", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "70", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}