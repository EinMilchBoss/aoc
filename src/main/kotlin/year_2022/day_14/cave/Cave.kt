package year_2022.day_14.cave

import year_2022.day_14.orientation.Coordinate

private val SAND_SOURCE = Coordinate(500, 0)

data class Cave(private val rockPaths: List<RockPath>) {
    val rockCoordinates: Set<Coordinate>

    init {
        rockCoordinates = rockCoordinatesOfPaths()
    }

    private fun rockCoordinatesOfPaths(): Set<Coordinate> =
        rockPaths.flatMap(RockPath::rockCoordinatesOfPath)
            .toSet()
}