package year_2022.day_14

import year_2022.day_14.aoc.*
import year_2022.day_14.cave.Cave
import year_2022.day_14.cave.parseRockPaths

fun String.partOne(): String {
    // build cave
    // split input
    // - each line separate by " -> "
    // convert coordinates to usable data class
    //

    val cave = Cave(parseRockPaths())

    println(parseRockPaths())
    println(cave.rockCoordinates)

    return ""
}

fun String.partTwo(): String =
    ""

fun main() {
    val inputs = Inputs(Exercise(2022, 13))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("24"))
    println(two.testProtocol(""))

    println("Part 1: ${one.run()}")
    println("Part 2: ${two.run()}")
}