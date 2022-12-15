package year_2022.day_10

import utils.test
import java.io.File

data class Command(val instruction: String, val parameter: Int?)

data class RegisterState(val during: Int, val after: Int)

typealias RegisterHistory = MutableMap<Int, RegisterState>
typealias MutableRegisterHistory = MutableMap<Int, RegisterState>
typealias Crt = List<List<Char>>
typealias MutableCrt = MutableList<MutableList<Char>>

fun RegisterHistory.signalStrength(): Int =
    let { signals ->
        (20..220 step 40).sumOf { cycle ->
            signals[cycle]!!.during * cycle
        }
    }

fun List<String>.parse(): List<Command> =
    map { line ->
        line.split(' ').let { command ->
            Command(command.first(), command.getOrNull(1)?.toInt())
        }
    }

fun MutableRegisterHistory.addEntries(vararg entries: Pair<Int, RegisterState>): MutableRegisterHistory =
    apply {
        putAll(entries)
    }

fun List<Command>.executeAll(): RegisterHistory {
    fun iterate(commandIndex: Int, cycle: Int, history: MutableRegisterHistory): RegisterHistory =
        if (commandIndex > lastIndex) history
        else this[commandIndex].let { (instruction, parameter) ->
            if (instruction == "addx") iterate(
                commandIndex + 1,
                cycle + 2,
                history.addEntries(
                    cycle to RegisterState(history[cycle - 1]?.after!!, history[cycle - 1]?.after!!),
                    cycle + 1 to RegisterState(history[cycle - 1]?.after!!, history[cycle - 1]?.after!! + parameter!!)
                )
            )
            else iterate(
                commandIndex + 1,
                cycle + 1,
                history.addEntries(cycle to RegisterState(history[cycle - 1]?.after!!, history[cycle - 1]?.after!!))
            )
        }
    return iterate(0, 1, mutableMapOf(0 to RegisterState(1, 1)))
}

fun solveFirst(input: List<String>): String =
    input.parse()
        .executeAll()
        .signalStrength()
        .toString()

fun RegisterHistory.image(): Crt {
    fun iterate(crt: MutableCrt, pixelPosition: Int): Crt =
        if (pixelPosition >= 240) crt
        else
            if (pixelPosition % 40 in (this[pixelPosition]!!.after - 1..this[pixelPosition]!!.after + 1))
                iterate(crt.apply { this[pixelPosition / 40][pixelPosition % 40] = '#' }, pixelPosition + 1)
            else
                iterate(crt, pixelPosition + 1)
    return iterate(MutableList(6) { MutableList(40) { '.' } }, 0)
}

fun solveSecond(input: List<String>): String =
    input.parse()
        .executeAll()
        .image()
        .joinToString("\n") { it.joinToString("") }

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_10"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "13140", ::solveFirst)}")
    println(
        "Second test: ${
            test(
                exampleInput, 
                """
                |##..##..##..##..##..##..##..##..##..##..
                |###...###...###...###...###...###...###.
                |####....####....####....####....####....
                |#####.....#####.....#####.....#####.....
                |######......######......######......####
                |#######.......#######.......#######.....
                """.trimMargin(), 
                ::solveSecond
            )
        }"
    )

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: \n${solveSecond(input)}")
}