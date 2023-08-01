package year_2022.day_11

import utils.aoc.*
import java.math.BigInteger

data class Monkey(
    val worryLevels: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val testDivisor: BigInteger,
    val monkeyOnTrue: Int,
    val monkeyOnFalse: Int,
) {
    var inspectionCount = BigInteger("0")
        private set

    fun inspect(divisorProduct: BigInteger? = null): List<BigInteger> =
        worryLevels.onEachIndexed { index, worryLevel ->
            worryLevels[index] = operation(optimize(worryLevel, divisorProduct))
            inspectionCount++
        }

    fun calmDown(): List<BigInteger> =
        worryLevels.onEachIndexed { index, worryLevel ->
            worryLevels[index] = worryLevel / BigInteger("3")
        }

    fun throwToNext(monkeys: List<Monkey>) {
        val (succeeded, failed) = worryLevels.partition(::test)
        monkeys[monkeyOnTrue].worryLevels.addAll(succeeded)
        monkeys[monkeyOnFalse].worryLevels.addAll(failed)
        worryLevels.clear()
    }

    fun test(dividend: BigInteger): Boolean =
        dividend % testDivisor == BigInteger("0")

    private fun optimize(worryLevel: BigInteger, divisorProduct: BigInteger?): BigInteger =
        if (divisorProduct == null) worryLevel
        else worryLevel % divisorProduct
}

fun String.formulateOperation(): (BigInteger) -> BigInteger {
    val (_, operator, right) = split(" ")
    val value = if (right == "old") null else right.toBigInteger()

    return if (operator == "*") { old: BigInteger -> old * (value ?: old) }
    else { old: BigInteger -> old + (value ?: old) }
}

fun List<String>.parse(): List<Monkey> =
    joinToString("\n")
        .split("\n\n")
        .map { monkey ->
            val (worryLevels, operation, test, monkeyOnTrue, monkeyOnFalse) = monkey
                .split("\n")
                .drop(1)
                .map {
                    it.split(": ")
                        .last()
                }

            Monkey(
                worryLevels.split(", ")
                    .map(String::toBigInteger)
                    .toMutableList(),
                operation.split(" = ")
                    .last()
                    .formulateOperation(),
                test.split(" ")
                    .last()
                    .toBigInteger(),
                monkeyOnTrue.split(" ")
                    .last()
                    .toInt(),
                monkeyOnFalse.split(" ")
                    .last()
                    .toInt()
            )
        }

fun List<Monkey>.highestMonkeyBusiness(): BigInteger =
    map(Monkey::inspectionCount)
        .sortedDescending()
        .take(2)
        .let { (left, right) -> left * right }

fun List<String>.solve(algorithm: (List<Monkey>) -> Unit) =
    parse()
        .also(algorithm)
        .highestMonkeyBusiness()
        .toString()

fun String.partOne(): String =
    lines().solve { monkeys ->
        repeat(20) {
            monkeys.forEach { monkey ->
                monkey.inspect()
                monkey.calmDown()
                monkey.throwToNext(monkeys)
            }
        }
    }

fun String.partTwo(): String =
    lines().solve { monkeys ->
        val divisorProduct = monkeys.map(Monkey::testDivisor)
            .reduce { acc, current -> acc * current }
        repeat(10_000) {
            monkeys.forEach { monkey ->
                monkey.inspect(divisorProduct)
                monkey.throwToNext(monkeys)
            }
        }
    }

fun main() {
    val inputs = Inputs(Exercise(2022, 11))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("10605"))
    println(two.testProtocol("2713310158"))

    println("Part 1: ${one.run()}")
    println("Part 2: ${two.run()}")
}