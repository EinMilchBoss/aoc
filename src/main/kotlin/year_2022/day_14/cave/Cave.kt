package year_2022.day_14.cave

import year_2022.day_14.orientation.Coordinate
import year_2022.day_14.orientation.Direction

data class Cave(private val rockPaths: List<RockPath>) {
    val rockCoordinates: Set<Coordinate> = rockCoordinatesOfPaths()
    private val caughtSandUnits = mutableListOf<Coordinate>()

    fun caughtSandUnits(): List<Coordinate> =
        caughtSandUnits

    fun dropSandUnitFrom(origin: Coordinate) {
        moveSandDown(origin)
    }

    private tailrec fun moveSandDown(current: Coordinate) {
        if (current.isLowerThanLowestRockWall()) return

        val fallenSand = current + Direction.DOWN.step
        if (fallenSand.isBlocked()) {
            moveSandDiagonally(current)
        } else {
            moveSandDown(fallenSand)
        }
    }

    private fun moveSandDiagonally(origin: Coordinate) {
        val leftDiagonal = origin + Direction.DOWN.step + Direction.LEFT.step
        val rightDiagonal = origin + Direction.DOWN.step + Direction.RIGHT.step
        when {
            !leftDiagonal.isBlocked() -> moveSandDown(leftDiagonal)
            !rightDiagonal.isBlocked() -> moveSandDown(rightDiagonal)
            else -> caughtSandUnits.add(origin)
        }
    }

    private fun Coordinate.isBlocked(): Boolean =
        this in this@Cave.rockCoordinates || this in this@Cave.caughtSandUnits

    private fun Coordinate.isLowerThanLowestRockWall(): Boolean =
        y >= lowestYOfRockWall()

    private fun lowestYOfRockWall(): Int =
        try {
            rockCoordinates.maxOf { (_, y) -> y }
        } catch (_: NoSuchElementException) {
            0
        }

    private fun rockCoordinatesOfPaths(): Set<Coordinate> =
        rockPaths.flatMap(RockPath::rockCoordinatesOfPath)
            .toSet()
}