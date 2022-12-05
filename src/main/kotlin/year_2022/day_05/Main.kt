package year_2022.day_05

import utils.pop
import utils.test
import java.io.File
import java.util.*

data class Instruction(val amount: Int, val from: Int, val to: Int)

fun solveFirst(input: List<String>): String {
    val inter = input.joinToString("\n")
        .split("\n\n")
        .let { (crateBlock, instructionBlock) ->
            Pair(
                crateBlock.split("\n")
                    .dropLast(1)
                    .map { line ->
                        (1..33 step 4).mapNotNull { index ->
                            line.getOrElse(index) { ' ' }
                        }
                    }
                    .foldRight(List(9) { Stack<Char>() }) { chars, acc ->
                        acc.also { stacks ->
                            stacks.forEachIndexed { index, stack ->
                                chars[index].let { if (!it.isWhitespace()) stack.push(it) }
                            }
                        }
                    },
                instructionBlock.split("\n")
                    .map { line ->
                        line.split(' ')
                            .let { Instruction(it[1].toInt(), it[3].toInt(), it[5].toInt()) }
                    }
            )
        }
        .let { (crates, instructions) ->
            instructions.forEach { instruction ->
                repeat(instruction.amount) {
                    crates[instruction.from - 1].pop()
                        .let { popped ->
                            crates[instruction.to - 1].push(popped)
                        }
                }
            }
            crates.map { it.lastOrNull()?.toString() ?: "" }
        }

    return inter.joinToString("")
}

fun solveSecond(input: List<String>): String {
    val inter = input.joinToString("\n")
        .split("\n\n")
        .let { (crateBlock, instructionBlock) ->
            Pair(
                crateBlock.split("\n")
                    .dropLast(1)
                    .map { line ->
                        (1..33 step 4).mapNotNull { index ->
                            line.getOrElse(index) { ' ' }
                        }
                    }
                    .foldRight(List(9) { mutableListOf<Char>() }) { chars, acc ->
                        acc.also { lists ->
                            lists.forEachIndexed { index, list ->
                                chars[index].let { if (!it.isWhitespace()) list.add(it) }
                            }
                        }
                    },
                instructionBlock.split("\n")
                    .map { line ->
                        line.split(' ')
                            .let { Instruction(it[1].toInt(), it[3].toInt(), it[5].toInt()) }
                    }
            )
        }
        .let { (crates, instructions) ->
            instructions.forEach { instruction ->
                crates[instruction.from - 1].pop(instruction.amount)
                    .let { removed ->
                        crates[instruction.to - 1].addAll(removed)
                    }
            }
            crates.map { it.lastOrNull()?.toString() ?: "" }
        }

    return inter.joinToString("")
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