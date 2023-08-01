package year_2022.day_03

import utils.aoc.*

fun Char.priority(): Int =
    if (this in 'a'..'z') this - 'a' + 1
    else this - 'A' + 27

fun String.splitInHalf(): Pair<String, String> =
    (length / 2).let { halfSize ->
        take(halfSize) to takeLast(halfSize)
    }

fun String.partOne(): String =
    lines().map(String::splitInHalf)
        .map { (compartmentA, compartmentB) ->
            compartmentA.first { it in compartmentB }
        }
        .sumOf(Char::priority)
        .toString()

fun String.partTwo(): String =
    lines().chunked(3)
        .map { (rucksackA, rucksackB, rucksackC) ->
            rucksackA.first { it in rucksackB && it in rucksackC }
        }
        .sumOf(Char::priority)
        .toString()

fun main() {
    val inputs = Inputs(Exercise(2022, 3))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("157"))
    println(two.testProtocol("70"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}