package year_2022.day_04

import utils.aoc.*

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

fun String.partOne(): String =
    lines().solve { (assignmentA, assignmentB) ->
        assignmentA.all { it in assignmentB } || assignmentB.all { it in assignmentA }
    }
        .toString()

fun String.partTwo(): String =
    lines().solve { (assignmentA, assignmentB) ->
        assignmentA.any { it in assignmentB } || assignmentB.any { it in assignmentA }
    }
        .toString()

fun main() {
    val inputs = Inputs(Exercise(2022, 4))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("2"))
    println(two.testProtocol("4"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}