package template

import java.io.File

fun solveFirst(input: List<String>): String {
    return ""
}

fun solveSecond(input: List<String>): String {
    return ""
}

fun test(input: List<String>, expected: String, solver: (List<String>) -> String): String =
    solver(input).let { actual ->
        if (actual != expected)
            "Example failed. $expected expected, got $actual."
        else
            "Example succeeded!"
    }

fun main() {
    val pathPrefix = "./src/main/kotlin/[...]"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "[...]", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "[...]", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}