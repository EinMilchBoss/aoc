package year_2022.day_12

import utils.test
import java.io.File

data class Position(val x: Int, val y: Int) {
    fun neighbors(): List<Position> {
        val up = Position(x, y + 1)
        val down = Position(x, y - 1)
        val right = Position(x + 1, y)
        val left = Position(x - 1, y)
        return listOf(up, down, right, left)
    }
}

data class Tile(val value: Char, val position: Position)

typealias Grid = List<List<Tile>>

const val START_CHAR = 'S'
const val END_CHAR = 'E'

fun Tile.height() =
    when (value) {
        START_CHAR -> 'a'
        END_CHAR -> 'z'
        else -> value
    }

fun isStepPossible(from: Tile, to: Tile): Boolean =
    to.height() <= from.height() + 1

fun Grid.shortestDistance(from: Tile, to: Tile): Int {
    // recursive call returns an int (which is the min amount of tiles needed to get to the end)

    // if you stand on the destination:
    // return the step count
    // get all EXISTING neighbour tiles, that are not visited yet (prevent endless loops)
    // check if the step to said neighbours is possible
    // if there are no valid tiles to go to:
    // return null
    // else:
    // add current tile to the list of visited tiles
    // increase the counter
    // go to next tile (recursive call)
    // save all these different routes into a list
    // return the min of the results.


    fun walk(currentTile: Tile, visited: List<Tile>): Int? {
        if (currentTile == to)
            return visited.size - 1

        val routes = currentTile.position
            .neighbors()
            .mapNotNull { neighborPosition ->
                val neighbor = tileOf(neighborPosition)
                if (neighbor != null && isStepPossible(currentTile, neighbor) && neighbor !in visited) {
                    walk(neighbor, visited + listOf(neighbor))
                } else {
                    null
                }
            }

        return if (routes.isNotEmpty())
            routes.min()
        else
            null
    }
    return walk(from, listOf(from))!!
}

fun Grid.tileOf(position: Position): Tile? =
    this.getOrNull(position.y)?.getOrNull(position.x)

fun Grid.tileOf(char: Char): Tile =
    flatten().first { (value) -> value == char }

fun List<String>.parse(): Grid =
    mapIndexed { y, line ->
        line.mapIndexed { x, value ->
            Tile(value, Position(x, y))
        }
    }

fun solveFirst(input: List<String>): String {
    val grid = input.parse()

    val start = grid.tileOf(START_CHAR)
    val end = grid.tileOf(END_CHAR)


    // grid of chars
    // x and y at S
    // recursively
    //  is top down left right only one higher than the current char or the same or lower?
    //  recursive call to changed x and y with step counter increase
    //  if the reached char is E then return the step counter

    return grid.shortestDistance(start, end).toString()
}


fun solveSecond(input: List<String>): String {
    return ""
}

fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_12"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "31", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}