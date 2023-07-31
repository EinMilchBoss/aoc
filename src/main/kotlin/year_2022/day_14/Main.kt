package year_2022.day_14

import year_2022.day_14.aoc.*
import year_2022.day_14.cave.*
import year_2022.day_14.orientation.Coordinate

fun String.partOne(): String {
    val sandSource = Coordinate(500, 0)
    val cave = Cave(parseRockPaths())
    return cave.maxAmountOfSandUntilOverflow(sandSource)
        .toString()
}

fun Cave.maxAmountOfSandUnitsUntilSandBlocksSource(source: Coordinate): Int {


    // dropSandUntilItFallsIndefinitely(source) { hasReached(yOfFloor) }

    return caughtSandUnits().size
}

fun Coordinate.hasReached(y: Int): Boolean =
    this.y == y

fun String.partTwo(): String {
    val sandSource = Coordinate(500, 0)
    val cave = Cave(parseRockPaths())

    val x = cave.maxAmountOfSandUnitsUntilSandBlocksSource(sandSource)

    return x.toString()
}

fun main() {
    val inputs = Inputs(Exercise(2022, 13))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("24"))
    //println(two.testProtocol("93"))

    println("Part 1: ${one.run()}")
    //println("Part 2: ${two.run()}")
}