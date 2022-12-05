package year_2022.day_05

import utils.test
import java.io.File
import java.util.*

data class Instruction(val amount: Int, val from: Int, val to: Int)

fun List<CrateStack>.performAll(
    instructions: List<Instruction>,
    shift: (List<Char>) -> List<Char>
): List<CrateStack> =
    also {
        instructions.forEach { instruction ->
            it[instruction.to - 1].pushAll(
                shift(it[instruction.from - 1].pop(instruction.amount))
            )
        }
    }

class CrateStack : Stack<Char>() {
    fun peekOrNull(): Char? =
        try {
            peek()
        } catch (_: EmptyStackException) {
            null
        }

    fun pushAll(elements: List<Char>): List<Char> =
        elements.onEach { push(it) }

    fun pop(n: Int) =
        List(n) { pop() }
}

fun List<CrateStack>.result(): String =
    joinToString("") { it.peekOrNull()?.toString() ?: "" }

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

fun List<String>.solve(algorithm: (Pair<List<CrateStack>, List<Instruction>>) -> String): String =
    joinToString("\n")
        .split("\n\n")
        .let { (crateBlock, instructionBlock) ->
            algorithm(crateBlock.parseCrates() to instructionBlock.parseInstructions())
        }

fun solveFirst(input: List<String>): String =
    input.solve { (crates, instructions) ->
        crates.performAll(instructions) { it }.result()
    }

fun solveSecond(input: List<String>): String =
    input.solve { (crates, instructions) ->
        crates.performAll(instructions) { it.reversed() }.result()
    }

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_05"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "CMZ", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "MCD", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}