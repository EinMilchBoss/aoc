package year_2022.day_08

import utils.test
import java.io.File

data class Tree(val x: Int, val y: Int, val height: Int)

typealias Grid = List<List<Tree>>

fun List<Tree>.visibleTrees(): List<Tree> {
    fun iterate(index: Int, highest: Int, matches: List<Tree>): List<Tree> =
        if (index > lastIndex) matches
        else this[index].run {
            if (height > highest) iterate(index + 1, height, matches + listOf(this))
            else iterate(index + 1, highest, matches)
        }
    return iterate(0, -1, emptyList())
}

fun List<String>.toGrid(): Grid =
    mapIndexed { y, line ->
        line.toCharArray()
            .mapIndexed { x, _ -> Tree(x, y, this[y][x].digitToInt()) }
    }

fun Grid.rows(isReversed: Boolean = false): Grid =
    if (!isReversed) this
    else map(List<Tree>::reversed)

fun Grid.cols(isReversed: Boolean = false): Grid =
    mapIndexed { y, line ->
        List(line.size) { x -> this[x][y] }.run {
            if (!isReversed) this
            else reversed()
        }
    }

fun solveFirst(input: List<String>): String =
    input.toGrid()
        .run {
            listOf(
                rows(),
                rows(true),
                cols(),
                cols(true)
            ).flatMap { grid -> grid.flatMap(List<Tree>::visibleTrees) }
        }
        .toSet()
        .count()
        .toString()


fun List<Tree>.visibleFromTreeHouse(house: Tree): List<Tree> {
    fun iterate(index: Int, matches: List<Tree>): List<Tree> =
        if (index > lastIndex) matches
        else this[index].let { tree ->
            if (tree.height < house.height) iterate(index + 1, matches + listOf(tree))
            else matches + listOf(tree)
        }
    return iterate(0, emptyList())
}

fun Grid.scenicScore(tree: Tree): Int {
    println("${tree.x}, ${tree.y}")
    // get tree in grid
    return listOf(
        rows()[tree.y].run { subList(tree.x, size) },
        rows()[tree.y].run { subList(0, tree.x + 1).reversed() },
        cols()[tree.x].run { subList(tree.y, size) },
        cols()[tree.x].run { subList(0, tree.y + 1).reversed() },
    ).map { it.drop(1) }
        .also(::println)
        .map { it.visibleFromTreeHouse(tree).count() }
        .also(::println)
        .reduce { acc, height -> acc * height}
        .also(::println)




//
//
//    val values = listOf(
//        rows().run { filterIndexed { index, _ -> index == tree.y }.subList(tree.x, size) }.first(),
//        rows(true).run { filterIndexed { index, _ -> index == tree.y }.subList(0, tree.x) }.first(),
//        cols().run { filterIndexed { index, _ -> index == tree.x }.subList(tree.x, size) }.first(),
//        cols(true).run { filterIndexed { index, _ -> index == tree.x }.subList(0, tree.x) }.first()
//    ).map(List<Tree>::visibleTrees).map(List<Tree>::count).also(::println)
//
//    return values.reduce { acc, scenery -> acc * scenery }
}

fun solveSecond(input: List<String>): String {
    return input.toGrid()
        .run {
            flatMap { trees -> trees.map(::scenicScore) }
        }
        .max()
        .toString()
}





fun main() {
    val pathPrefix = "./src/main/kotlin/year_2022/day_08"

    val exampleInput = File("$pathPrefix/example.txt").readLines()
    println("First test: ${test(exampleInput, "21", ::solveFirst)}")
    println("Second test: ${test(exampleInput, "8", ::solveSecond)}")

    val input = File("$pathPrefix/input.txt").readLines()
    println("First result: ${solveFirst(input)}")
    println("Second result: ${solveSecond(input)}")
}