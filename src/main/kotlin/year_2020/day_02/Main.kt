package year_2020.day_02

import utils.aoc.*

data class PasswordEntry(val first: Int, val second: Int, val char: Char, val password: String)

fun String.toPasswordEntry(): PasswordEntry =
    this.split(' ')
        .let { lineParts ->
            val (min, max) = lineParts[0].split('-')
                .map(String::toInt)
            val char = lineParts[1].first()
            val password = lineParts[2]

            PasswordEntry(min, max, char, password)
        }

fun String.partOne(): String =
    lines().map(String::toPasswordEntry)
        .count { entry ->
            entry.password.count { it == entry.char }
                .let { charCount ->
                    charCount >= entry.first && charCount <= entry.second
                }
        }
        .toString()

fun String.partTwo(): String =
    lines().map(String::toPasswordEntry)
        .count { entry ->
            listOf(
                entry.password[entry.first - 1], entry.password[entry.second - 1]
            ).count { it == entry.char } == 1
        }
        .toString()

fun main() {
    val inputs = Inputs(Exercise(2020, 2))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("2"))
    println(two.testProtocol("1"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}