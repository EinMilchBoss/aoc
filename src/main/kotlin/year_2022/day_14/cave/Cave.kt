package year_2022.day_14.cave

import year_2022.day_14.orientation.Coordinate
import year_2022.day_14.orientation.Direction

data class Cave(private val rockPaths: List<RockPath>) {
    val rockCoordinates: Set<Coordinate> = rockCoordinatesOfPaths()
    private val caughtSandUnits = mutableListOf<Coordinate>()

    fun caughtSandUnits(): List<Coordinate> =
        caughtSandUnits

    fun dropSandUnitFrom(origin: Coordinate) {
        if (origin.y >= maxY()) return

        val down = origin + Direction.DOWN.step
        if (down.isBlocked()) {
            val leftDiagonal = down + Direction.LEFT.step
            val rightDiagonal = down + Direction.RIGHT.step

            if (!leftDiagonal.isBlocked()) {
                dropSandUnitFrom(leftDiagonal)
            } else if (!rightDiagonal.isBlocked()) {
                dropSandUnitFrom(rightDiagonal)
            } else {
                caughtSandUnits.add(origin)
            }
        } else {
            dropSandUnitFrom(down)
        }
    }

    private fun Coordinate.isBlocked(): Boolean =
        this in this@Cave.rockCoordinates || this in this@Cave.caughtSandUnits

    private fun rockCoordinatesOfPaths(): Set<Coordinate> =
        rockPaths.flatMap(RockPath::rockCoordinatesOfPath)
            .toSet()

    private fun maxY(): Int =
        try {
            rockCoordinates.maxOf { (_, y) -> y }
        } catch (_: NoSuchElementException) {
            0
        }
}