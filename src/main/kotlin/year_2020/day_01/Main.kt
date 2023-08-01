package year_2020.day_01

import utils.aoc.*

fun multiplyTwoComplements(items: List<Int>, total: Int): Int =
    items.map { it to total - it }
        .filter { (_, complement) -> items.any { it == complement } }
        .map { it.first * it.second }
        .first()

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

fun String.partOne(): String {
    val inputInts = lines().map(String::toInt)
    return multiplyTwoComplements(inputInts, 2020).toString()
}

fun String.partTwo(): String {
    val inputInts = lines().map(String::toInt)
    return multiplyThreeComplements(inputInts, 2020).toString()
}

fun main() {
    val inputs = Inputs(Exercise(2020, 1))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("514579"))
    println(two.testProtocol("241861950"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}