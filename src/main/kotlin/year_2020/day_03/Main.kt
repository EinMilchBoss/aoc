package year_2020.day_03

import utils.aoc.*

fun walkPath(input: List<String>, right: Int, down: Int): Int {
    tailrec fun iterate(x: Int, y: Int, treeCount: Int): Int {
        val newY = y + down
        if (newY > input.lastIndex) return treeCount

        val newX = (x + right).let {
            if (it > input[y].lastIndex) it - input[y].length
            else it
        }

        val newTreeCount = if (input[newY][newX] == '#') treeCount + 1 else treeCount
        return iterate(newX, newY, newTreeCount)
    }
    return iterate(0, 0, 0)
}

fun String.partOne(): String =
    walkPath(lines(), 3, 1).toString()

fun String.partTwo(): String =
    lines().let { lines ->
        listOf(
            walkPath(lines, 1, 1),
            walkPath(lines, 3, 1),
            walkPath(lines, 5, 1),
            walkPath(lines, 7, 1),
            walkPath(lines, 1, 2)
        ).map(Int::toLong)
            .reduce { acc, i -> acc * i }
            .toString()
    }

fun main() {
    val inputs = Inputs(Exercise(2020, 3))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("7"))
    println(two.testProtocol("336"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}