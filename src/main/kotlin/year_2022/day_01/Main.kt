package year_2022.day_01

import utils.aoc.*

fun List<String>.splitBlockSums() =
    this.joinToString("\n")
        .split("\n\n")
        .map {
            it.split("\n")
                .map(String::toInt)
                .sum()
        }

fun String.partOne(): String =
    lines().splitBlockSums()
        .max()
        .toString()

fun String.partTwo(): String =
    lines().splitBlockSums()
        .sortedByDescending { it }
        .take(3)
        .sum()
        .toString()

fun main() {
    val inputs = Inputs(Exercise(2022, 1))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("24000"))
    println(two.testProtocol("45000"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}