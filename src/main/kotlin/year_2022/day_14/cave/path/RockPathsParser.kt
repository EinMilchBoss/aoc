package year_2022.day_14.cave.path

import utils.orientation.Coordinate

fun String.parseRockPaths(): List<RockPath> =
    lines().map(String::toRockPath)

private fun String.toRockPath(): RockPath =
    split(" -> ")
        .map(String::toCoordinate)
        .toRockPath()

private fun String.toCoordinate(): Coordinate =
    split(',')
        .map(String::toInt)
        .let { (x, y) -> Coordinate(x, y) }

private fun List<Coordinate>.toRockPath(): RockPath =
    RockPath(this)