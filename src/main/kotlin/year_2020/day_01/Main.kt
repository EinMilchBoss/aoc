package year_2020.day_01

import utils.test
import java.io.File

fun multiplyTwoComplements(items: List<Int>, total: Int): Int =
    items.map { it to total - it }.filter { (_, complement) -> items.any { it == complement } }
        .map { it.first * it.second }.first()

fun multiplyThreeComplements(items: List<Int>, total: Int): Int {
    for (i in 0..items.size - 3) {
        for (j in i + 1..items.size - 2) {
            for (k in j + 1..items.size - 1) {
                if (items[i] + items[j] + items[k] == total) return items[i] * items[j] * items[k]
            }
        }
    }

    throw Error("No valid complements found.")
}

fun solveFirst(input: List<String>): String {
    val inputInts = input.map(String::toInt)
    return multiplyTwoComplements(inputInts, 2020).toString()
}

fun solveSecond(input: List<String>): String {
    val inputInts = input.map(String::toInt)
    return multiplyThreeComplements(inputInts, 2020).toString()
}

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2020/day_01"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "514579", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "241861950", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}