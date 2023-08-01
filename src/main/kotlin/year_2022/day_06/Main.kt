package year_2022.day_06

import utils.aoc.*

fun String.allUnique(): Boolean =
    all(mutableSetOf<Char>()::add)

fun String.firstMarker(markerLength: Int): Int {
    tailrec fun String.iterate(startIndex: Int): Int =
        (startIndex + markerLength).let { endIndex ->
            substring(startIndex, endIndex).let<String, Int> { view ->
                return if (view.allUnique()) endIndex
                else iterate(startIndex + 1)
            }
        }

    return iterate(0)
}

fun List<String>.solve(markerLength: Int): String =
    first().firstMarker(markerLength)
        .toString()

fun String.partOne(): String =
    lines().solve(4)

fun String.partTwo(): String =
    lines().solve(14)

fun main() {
    val inputs = Inputs(Exercise(2022, 6))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("10"))
    println(two.testProtocol("29"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}