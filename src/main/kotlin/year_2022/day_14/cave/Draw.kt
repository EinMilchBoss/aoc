package year_2022.day_14.cave

import year_2022.day_14.orientation.Coordinate
import year_2022.day_14.util.Bounds

private const val ROCK = '#'
private const val SAND = 'O'
private const val AIR = '.'

fun Cave.draw(): String {
    val allCoordinates = rockCoordinates + caughtSandCoordinates
    val bounds = widthBounds(allCoordinates)
    val lines = mapOfLines(allCoordinates)
    return (0..lowestYOfRockWall()).joinToString("\n") { rowIndex ->
        if (lines.isLineEmpty(rowIndex)) drawEmptyLine(bounds)
        else drawLine(bounds, rowIndex)
    }
}

private fun widthBounds(allCoordinates: Set<Coordinate>): Bounds =
    Bounds(
        allCoordinates.extremeValueOfX(Set<Coordinate>::minBy),
        allCoordinates.extremeValueOfX(Set<Coordinate>::maxBy)
    )

private fun mapOfLines(allCoordinates: Set<Coordinate>) =
    allCoordinates.groupBy { (_, y) -> y }

private fun Map<Int, *>.isLineEmpty(index: Int) =
    index !in keys

private fun drawEmptyLine(bounds: Bounds): String =
    String(CharArray(bounds.length) { AIR })

private fun Set<Coordinate>.extremeValueOfX(algorithm: Set<Coordinate>.((Coordinate) -> Int) -> Coordinate): Int =
    algorithm { (x, _) -> x }.x

private fun Cave.drawLine(bounds: Bounds, rowIndex: Int): String =
    bounds.rangeInclusive()
        .map { columnIndex -> drawCell(rowIndex, columnIndex) }
        .joinToString("")

private fun Cave.drawCell(rowIndex: Int, columnIndex: Int): Char =
    when (Coordinate(columnIndex, rowIndex)) {
        in caughtSandCoordinates -> SAND
        in rockCoordinates -> ROCK
        else -> AIR
    }