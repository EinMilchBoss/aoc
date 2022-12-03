package aoc_2022_02

import relativeValue
import test
import java.io.File

enum class Result(val score: Int) {
    WIN(6),
    DRAW(3),
    LOSE(0)
}

enum class Command(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun weakness() =
        enumValues<Command>().let { values ->
            values.relativeValue(values.indexOf(this) + 1)
        }

    fun strength() =
        enumValues<Command>().let { values ->
            values.relativeValue(values.indexOf(this) - 1)
        }
}

data class CommandRound(val opponent: Command, val player: Command) {
    private fun result(): Result =
        when (player) {
            opponent.weakness() -> Result.WIN
            opponent.strength() -> Result.LOSE
            else -> Result.DRAW
        }

    fun points(): Int =
        result().score + player.score
}

data class ResultRound(val opponent: Command, val result: Result) {
    private fun player(): Command =
        when (result) {
            Result.WIN -> opponent.weakness()
            Result.LOSE -> opponent.strength()
            else -> opponent
        }

    fun points(): Int =
        result.score + player().score
}

fun String.translateCommand(): Command =
    listOf(
        listOf("A", "X") to Command.ROCK,
        listOf("B", "Y") to Command.PAPER,
        listOf("C", "Z") to Command.SCISSORS
    ).first { (from, _) ->
        from.any { this == it }
    }.second

fun String.translateResult(): Result =
    listOf(
        "X" to Result.LOSE,
        "Y" to Result.DRAW,
        "Z" to Result.WIN
    ).first { (from, _) ->
        this == from
    }.second

fun <T> List<String>.translateRounds(transform: (String, String) -> T): List<T> =
    map {
        it.split(' ')
            .let { (first, second) -> transform(first, second) }
    }

fun solveFirst(input: List<String>): String =
    input.translateRounds { opponent, player ->
        CommandRound(opponent.translateCommand(), player.translateCommand())
    }.map(CommandRound::points)
        .sum()
        .toString()

fun solveSecond(input: List<String>): String =
    input.translateRounds { opponent, result ->
        ResultRound(opponent.translateCommand(), result.translateResult())
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