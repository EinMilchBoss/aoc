package year_2022.day_14.cave

import year_2022.day_14.orientation.Coordinate
import year_2022.day_14.orientation.Direction

data class Cave(private val rockPaths: List<RockPath>) {
    val rockCoordinates = rockCoordinatesOfPaths()
    internal val caughtSandUnits = mutableListOf<Coordinate>()

    private fun rockCoordinatesOfPaths(): Set<Coordinate> =
        rockPaths.flatMap(RockPath::rockCoordinatesOfPath)
            .toSet()

    fun caughtSandUnits(): List<Coordinate> =
        caughtSandUnits

    tailrec fun dropSandUnitFrom(current: Coordinate) {
        if (current.isLowerThanLowestRockWall()) return

        val fallenSand = current + Direction.DOWN.step
        if (fallenSand.isBlocked()) {
            moveSandDiagonally(current)
        } else {
            dropSandUnitFrom(fallenSand)
        }
    }

    private fun moveSandDiagonally(current: Coordinate) {
        val leftDiagonal = current + Direction.DOWN.step + Direction.LEFT.step
        val rightDiagonal = current + Direction.DOWN.step + Direction.RIGHT.step
        when {
            !leftDiagonal.isBlocked() -> dropSandUnitFrom(leftDiagonal)
            !rightDiagonal.isBlocked() -> dropSandUnitFrom(rightDiagonal)
            else -> caughtSandUnits.add(current)
        }
    }

    private fun Coordinate.isLowerThanLowestRockWall(): Boolean =
        y >= lowestYOfRockWall()

    private fun lowestYOfRockWall(): Int =
        try {
            rockCoordinates.maxOf { (_, y) -> y }
        } catch (_: NoSuchElementException) {
            0
        }

    private fun Coordinate.isBlocked(): Boolean =
        this in this@Cave.rockCoordinates || this in this@Cave.caughtSandUnits
}