package year_2022.day_06

import utils.test
import java.io.File

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
    first().firstMarker(markerLength).toString()

fun solveFirst(input: List<String>): String =
    input.solve(4)

fun solveSecond(input: List<String>): String =
    input.solve(14)

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_06"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "10", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "29", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}