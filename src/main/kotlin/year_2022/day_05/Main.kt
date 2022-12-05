package year_2022.day_05

import utils.pop
import utils.test
import java.io.File
import java.util.*
import javax.swing.SortOrder

data class Instruction(val amount: Int, val from: Int, val to: Int)

fun List<Instruction>.performAll(
    crates: List<CrateStack>,
    shift: (destination: CrateStack, values: List<Char>) -> Unit
): List<CrateStack> =
    crates.also {
        forEach { instruction ->
            shift(
                crates[instruction.to - 1],
                crates[instruction.from - 1].pop(instruction.amount)
            )
        }
    }

data class CrateStack(private val values: MutableList<Char>) {
    fun peekOrNull(): Char? =
        values.lastOrNull()

    fun push(element: Char) =
        values.add(element)

    fun push(elements: List<Char>) =
        values.addAll(elements)

    fun pop(n: Int) =
        values.pop(n)
}

fun List<CrateStack>.result(): String =
    joinToString("") { it.peekOrNull()?.toString() ?: "" }

fun String.parseCrates(): List<CrateStack> =
    split("\n")
        .dropLast(1)
        .map { line ->
            (1..33 step 4).mapNotNull { index ->
                line.getOrElse(index) { ' ' }
            }
        }
        .foldRight(List(9) { CrateStack(mutableListOf()) }) { chars, acc ->
            acc.also { stacks ->
                stacks.forEachIndexed { index, stack ->
                    chars[index].let { if (!it.isWhitespace()) stack.push(it) }
                }
            }
        }

fun String.parseInstructions(): List<Instruction> =
    split("\n")
        .map { line ->
            line.split(' ')
                .let { Instruction(it[1].toInt(), it[3].toInt(), it[5].toInt()) }
        }

fun List<String>.parse(): Pair<List<CrateStack>, List<Instruction>> =
    joinToString("\n")
        .split("\n\n")
        .let { (crateBlock, instructionBlock) ->
            Pair(
                crateBlock.parseCrates(),
                instructionBlock.parseInstructions()
            )
        }

fun List<String>.solve(logic: (Pair<List<CrateStack>, List<Instruction>>) -> String): String =
    parse().let(logic)

fun solveFirst(input: List<String>): String =
    input.solve { (crates, instructions) ->
        instructions.performAll(crates) { destination, values ->
            destination.push(values.reversed())
        }.result()
    }

fun solveSecond(input: List<String>): String =
    input.solve { (crates, instructions) ->
        instructions.performAll(crates) { destination, values ->
            destination.push(values)
        }.result()
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