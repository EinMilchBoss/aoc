package year_2022.day_05

import utils.aoc.*
import java.util.*

data class Instruction(val amount: Int, val from: Int, val to: Int)

class CrateStack : Stack<Char>() {
    fun peekOrNull(): Char? =
        try {
            peek()
        } catch (_: EmptyStackException) {
            null
        }

    fun pushAll(elements: List<Char>): List<Char> =
        elements.onEach { push(it) }

    fun pop(n: Int): List<Char> =
        List(n) { pop() }
}

fun List<CrateStack>.performAll(
    instructions: List<Instruction>,
    shift: (List<Char>) -> List<Char>,
): List<CrateStack> =
    also {
        instructions.forEach { (amount, from, to) ->
            it[to - 1].pushAll(
                shift(it[from - 1].pop(amount))
            )
        }
    }

fun List<CrateStack>.result(): String =
    joinToString("") {
        it.peekOrNull()
            ?.toString() ?: ""
    }

fun String.parseCrates(): List<CrateStack> =
    split("\n")
        .dropLast(1)
        .map { line ->
            (1..33 step 4).map { crateIndex ->
                line.getOrNull(crateIndex)
                    ?.let { if (!it.isWhitespace()) it else null }
            }
        }
        .foldRight(List(9) { CrateStack() }) { chars, acc ->
            acc.onEachIndexed { index, stack ->
                chars[index]?.let { stack.push(it) }
            }
        }

fun String.parseInstructions(): List<Instruction> =
    split("\n")
        .map { line ->
            line.split(' ')
                .let { Instruction(it[1].toInt(), it[3].toInt(), it[5].toInt()) }
        }

fun List<String>.solve(algorithm: (Pair<List<CrateStack>, List<Instruction>>) -> List<CrateStack>): String =
    joinToString("\n")
        .split("\n\n")
        .let { (crateBlock, instructionBlock) ->
            algorithm(crateBlock.parseCrates() to instructionBlock.parseInstructions())
        }
        .result()

fun String.partOne(): String =
    lines().solve { (crates, instructions) ->
        crates.performAll(instructions) { it }
    }

fun String.partTwo(): String =
    lines().solve { (crates, instructions) ->
        crates.performAll(instructions) { it.reversed() }
    }

fun main() {
    val inputs = Inputs(Exercise(2022, 5))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("CMZ"))
    println(two.testProtocol("MCD"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}