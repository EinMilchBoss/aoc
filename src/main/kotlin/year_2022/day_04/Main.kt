package year_2022.day_04

import utils.test
import java.io.File

fun List<String>.solve(condition: (Pair<IntRange, IntRange>) -> Boolean): Int =
    map { line ->
        line.split(',')
            .map { range ->
                range.split('-')
                    .map(String::toInt)
                    .let { (first, second) -> first..second }
            }
            .zipWithNext()
            .first()
            .let(condition)
    }.count { it }

fun solveFirst(input: List<String>): String =
    input.solve { (assignmentA, assignmentB) ->
        assignmentA.all { it in assignmentB } || assignmentB.all { it in assignmentA }
    }.toString()

fun solveSecond(input: List<String>): String =
    input.solve { (assignmentA, assignmentB) ->
        assignmentA.any { it in assignmentB } || assignmentB.any { it in assignmentA }
    }.toString()

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_04"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "2", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "4", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}