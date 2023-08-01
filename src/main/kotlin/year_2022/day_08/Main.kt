package year_2022.day_08

import utils.aoc.*

data class Tree(val x: Int, val y: Int, val height: Int)

typealias TreeGrid = List<List<Tree>>

fun TreeGrid.rowsToCols(): TreeGrid =
    mapIndexed { y, line ->
        List(line.size) { x -> this[x][y] }
    }

fun TreeGrid.scenicScore(tree: Tree): Int =
    listOf(
        this[tree.y].run { subList(tree.x + 1, size) },
        this[tree.y].run { subList(0, tree.x).reversed() },
        rowsToCols()[tree.x].run { subList(tree.y + 1, size) },
        rowsToCols()[tree.x].run { subList(0, tree.y).reversed() },
    ).map {
        it.visibleFromTreeHouse(tree)
            .count()
    }
        .reduce { acc, height -> acc * height }

fun List<Tree>.visibleFromGround(): List<Tree> {
    tailrec fun iterate(index: Int, highest: Int, matches: List<Tree>): List<Tree> =
        if (index > lastIndex) matches
        else
            if (this[index].height > highest) iterate(index + 1, this[index].height, matches + listOf(this[index]))
            else iterate(index + 1, highest, matches)
    return iterate(0, -1, emptyList())
}

fun List<Tree>.visibleFromTreeHouse(house: Tree): List<Tree> {
    tailrec fun iterate(index: Int, matches: List<Tree>): List<Tree> =
        if (index > lastIndex) matches
        else
            if (this[index].height < house.height) iterate(index + 1, matches + listOf(this[index]))
            else matches + listOf(this[index])
    return iterate(0, emptyList())
}

fun List<String>.toGrid(): TreeGrid =
    mapIndexed { y, line ->
        line.toCharArray()
            .mapIndexed { x, _ -> Tree(x, y, this[y][x].digitToInt()) }
    }

fun String.partOne(): String =
    lines().toGrid()
        .run {
            listOf(
                this,
                map(List<Tree>::reversed),
                rowsToCols(),
                rowsToCols().map(List<Tree>::reversed)
            ).flatMap { it.flatMap(List<Tree>::visibleFromGround) }
        }
        .toSet()
        .count()
        .toString()

fun String.partTwo(): String =
    lines().toGrid()
        .run {
            flatMap { trees -> trees.map(::scenicScore) }
        }
        .max()
        .toString()

fun main() {
    val inputs = Inputs(Exercise(2022, 8))
    val one = Part.one(inputs, String::partOne)
    val two = Part.two(inputs, String::partTwo)

    println(one.testProtocol("21"))
    println(two.testProtocol("8"))

    println("Part 1:\n${one.run()}")
    println("Part 2:\n${two.run()}")
}