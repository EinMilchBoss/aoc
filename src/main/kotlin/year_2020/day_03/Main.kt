package year_2020.day_03

import utils.test
import java.io.File

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

fun solveFirst(input: List<String>): String = walkPath(input, 3, 1).toString()

fun solveSecond(input: List<String>): String = listOf(
    walkPath(input, 1, 1),
    walkPath(input, 3, 1),
    walkPath(input, 5, 1),
    walkPath(input, 7, 1),
    walkPath(input, 1, 2)
).map(Int::toLong).reduce { acc, i -> acc * i }.toString()

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2020/day_03"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "7", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "336", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}