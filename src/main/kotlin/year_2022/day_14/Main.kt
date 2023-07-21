package year_2022.day_14

import year_2022.day_14.aoc.*
import year_2022.day_14.cave.Cave
import year_2022.day_14.cave.parseRockPaths
import year_2022.day_14.orientation.Coordinate

fun String.partOne(): String {
    // cave saves every sand unit into a mutable list of coordinates
    // dropSandUnitFrom(origin: Coordinate) does not try any further if sand.y <= lowestRockCoordinate.y
    // logic:
    // val originalSandAmount = cave.caughtSandUnits()
    // cave.dropSandUnitFrom(sandSource)
    // if (cave.sandUnits == originalSandAmount) {
    // return cave.sandUnits()
    // } else /* iterate further */
    val sandSource = Coordinate(500, 0)
    val cave = Cave(parseRockPaths())

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