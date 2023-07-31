package year_2022.day_14.cave

import year_2022.day_14.orientation.Coordinate

fun Cave.maxAmountOfSandUntilSourceBlocked(): Int {
    val yOfFloor = lowestYOfRockWall() + 2
    val widthPerSide = yOfFloor + 1
    val floor = RockPath(listOf(Coordinate(-widthPerSide, yOfFloor), Coordinate(widthPerSide, yOfFloor)))
    rockCoordinates.addAll(floor.rockCoordinatesOfPath())

    println(floor)
    println(rockCoordinates)

    return 0
}