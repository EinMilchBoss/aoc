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
    (20..220 step 40).sumOf { cycle ->
        this[cycle]!!.during * cycle
    }

fun MutableRegisterHistory.addEntries(vararg entries: Pair<Int, RegisterState>): MutableRegisterHistory =
    apply { putAll(entries) }

fun List<Command>.executeAll(): RegisterHistory {
    fun iterate(commandIndex: Int, cycle: Int, history: MutableRegisterHistory): RegisterHistory =
        if (commandIndex > lastIndex) history
        else this[commandIndex].let { (instruction, parameter) ->
            history[cycle - 1]?.after!!.let { previous ->
                if (instruction == "addx") iterate(
                    commandIndex + 1,
                    cycle + 2,
                    history.addEntries(
                        cycle to RegisterState(previous, previous),
                        cycle + 1 to RegisterState(previous, previous + parameter!!)
                    )
                )
                else iterate(
                    commandIndex + 1,
                    cycle + 1,
                    history.addEntries(cycle to RegisterState(previous, previous))
                )
            }
        }
    return iterate(0, 1, mutableMapOf(0 to RegisterState(1, 1)))
}

fun RegisterHistory.spritePositionRange(pixelPosition: Int): IntRange =
    (this[pixelPosition]!!.after - 1)..(this[pixelPosition]!!.after + 1)

fun MutableCrt.turnOn(pixelPosition: Int, crtLineSize: Int): MutableCrt =
    apply { this[pixelPosition / crtLineSize][pixelPosition % crtLineSize] = '#' }

fun RegisterHistory.image(): Crt {
    val crtLineSize = 40
    val crtSize = 6 * crtLineSize
    tailrec fun iterate(pixelPosition: Int, crt: MutableCrt): Crt =
        if (pixelPosition >= crtSize) crt
        else
            if ((pixelPosition % crtLineSize) in spritePositionRange(pixelPosition))
                iterate(pixelPosition + 1, crt.turnOn(pixelPosition, crtLineSize))
            else
                iterate(pixelPosition + 1, crt)
    return iterate(0, MutableList(6) { MutableList(40) { '.' } })
}

fun List<String>.parse(): List<Command> =
    map { line ->
        line.split(' ').let { command ->
            Command(command.first(), command.getOrNull(1)?.toInt())
        }
    }

fun solveFirst(input: List<String>): String =
    input.parse()
        .executeAll()
        .signalStrength()
        .toString()

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