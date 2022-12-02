package aoc_2022_02

import test
import java.io.File

enum class Command(val pointAmount: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun weakness() =
        enumValues<Command>().let { values ->
            (values.indexOf(this) + 1).let { index ->
                val actualIndex = if (index > values.lastIndex) index - values.size
                else index

                values[actualIndex]
            }
        }

    fun strength() =
        enumValues<Command>().let { values ->
            (values.indexOf(this) - 1).let { index ->
                val actualIndex = if (index < 0) index + values.size
                else index

                values[actualIndex]
            }
        }
}

enum class Result(val pointAmount: Int) { WIN(6), LOSE(0), DRAW(3) }

data class CommandRound(val opponent: Command, val player: Command) {
    private fun result(): Result {
        if (player == opponent.weakness()) return Result.WIN
        if (opponent == player.weakness()) return Result.LOSE
        return Result.DRAW
    }

    fun points(): Int =
        result().pointAmount + player.pointAmount
}

data class ResultRound(val opponent: Command, val result: Result) {
    private fun player(): Command =
        when (result) {
            Result.WIN -> opponent.weakness()
            Result.LOSE -> opponent.strength()
            else -> opponent
        }

    fun points(): Int =
        result.pointAmount + player().pointAmount
}

fun String.translateCommand(): Command =
    listOf(
        listOf("A", "X") to Command.ROCK,
        listOf("B", "Y") to Command.PAPER,
        listOf("C", "Z") to Command.SCISSORS
    ).let { translation ->
        translation.first { (from, _) ->
            from.any { this == it }
        }.second
    }

fun String.translateResult(): Result =
    listOf(
        "X" to Result.LOSE,
        "Y" to Result.DRAW,
        "Z" to Result.WIN
    ).let { translation ->
        translation.first { (from, _) ->
            this == from
        }.second
    }

fun solveFirst(input: List<String>): String =
    input.map {
        it.split(' ')
            .map(String::translateCommand)
            .let { (opponent, player) -> CommandRound(opponent, player) }
    }.map(CommandRound::points)
        .sum()
        .toString()

fun solveSecond(input: List<String>): String =
    input.map {
        it.split(' ')
            .let { (opponent, result) ->
                ResultRound(opponent.translateCommand(), result.translateResult())
            }
    }.map(ResultRound::points)
        .sum()
        .toString()

fun main() {
    val pathPrefix = "./src/main/kotlin/aoc_2022_02"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "15", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "12", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}