package year_2022.day_14.orientation

import year_2022.day_14.cave.IllegalRockPathException
import kotlin.math.absoluteValue

data class Coordinate(val x: Int, val y: Int) {
    operator fun plus(other: Coordinate): Coordinate =
        Coordinate(x + other.x, y + other.y)

    operator fun minus(other: Coordinate): Coordinate =
        Coordinate(x - other.x, y - other.y)

    fun scale(multiplier: Int): Coordinate =
        Coordinate(multiplier * x, multiplier * y)

    fun directionTo(other: Coordinate): Direction {
        val delta = other - this
        return when {
            delta.y > 0 -> Direction.DOWN
            delta.y < 0 -> Direction.UP
            delta.x > 0 -> Direction.RIGHT
            delta.x < 0 -> Direction.LEFT
            else -> throw IllegalRockPathException()
        }
    }

    fun manhattanDistanceTo(other: Coordinate): Int =
        (other.x - this.x).absoluteValue + (other.y - this.y).absoluteValue
}