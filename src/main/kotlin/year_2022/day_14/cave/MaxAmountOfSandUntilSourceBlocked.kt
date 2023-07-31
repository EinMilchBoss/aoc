package year_2022.day_14.cave

import year_2022.day_14.orientation.Coordinate

fun Cave.maxAmountOfSandUntilSourceBlocked(source: Coordinate): Int {
    addFloorRockCoordinates(source)
    dropSandUntilNothingChanges(source)
    return caughtSandCoordinates.size
}

private fun Cave.addFloorRockCoordinates(source: Coordinate) {
    val yOfFloor = lowestYOfRockWall() + 2
    val widthPerSide = yOfFloor + 1
    val floor = RockPath(
        listOf(
            Coordinate(source.x - widthPerSide, source.y + yOfFloor),
            Coordinate(source.x + widthPerSide, source.y + yOfFloor)
        )
    )
    rockCoordinates.addAll(floor.rockCoordinatesOfPath())
}