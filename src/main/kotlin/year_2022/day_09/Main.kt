package year_2022.day_09

import utils.aoc.*
import kotlin.math.absoluteValue

data class Command(val direction: Char, val stepCount: Int)

data class Position(var x: Int, var y: Int) {
    operator fun plus(other: Position): Position =
        Position(x + other.x, y + other.y)

    operator fun minus(other: Position): Position =
        Position(x - other.x, y - other.y)
}

data class Rope(val knotAmount: Int) {
    private val knots = MutableList(knotAmount) { Position(0, 0) }

    var head: Position
        get() = knots.first()
        set(value) {
            knots[0] = value
        }

    var tail: Position
        get() = knots.last()
        set(value) {
            knots[knots.lastIndex] = value
        }

    fun move(offset: Position) {
        head += offset
        knots.indices.drop(1)
            .forEach { knotIndex ->
                if (!knots[knotIndex].isTouching(knots[knotIndex - 1])) {
                    knots[knotIndex] += knots[knotIndex].approximate(knots[knotIndex - 1])
                }
            }
    }
}

fun List<String>.parse(): List<Command> =
    map {
        it.split(' ')
            .let { (direction, stepAmount) ->
                Command(direction.first(), stepAmount.toInt())
            }
    }

fun Position.isTouching(other: Position): Boolean =
    (this - other).let { (dx, dy) ->
        dx in -1..1 && dy in -1..1
    }

fun Int.safeDivide(divisor: Int): Int =
    if (divisor == 0) 0
    else this / divisor

fun Position.approximate(other: Position) =
    (other - this).let { (dx, dy) ->
        Position(
            dx.safeDivide(dx.absoluteValue),
            dy.safeDivide(dy.absoluteValue)
        )
    }

fun Char.toStep(): Position =
    when (this) {
        'U' -> Position(0, 1)
        'D' -> Position(0, -1)
        'R' -> Position(1, 0)
        else -> Position(-1, 0)
    }

fun List<String>.solve(knotAmount: Int): String =
    Rope(knotAmount).let { rope ->
        parse()
            .flatMap { (direction, stepAmount) ->
                List(stepAmount) {
                    rope.move(direction.toStep())
                    rope.tail
                }
            }
            .toSet()
            .count()
            .toString()
    }

fun String.partOne(): String =
    lines().solve(2)

fun String.partTwo(): String =
    lines().solve(10)

fun main() {
    val inputs = Inputs(Exercise(2022, 9))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("13"))
    println(two.testProtocol("1"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}