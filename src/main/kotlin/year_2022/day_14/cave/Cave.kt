package year_2022.day_14.cave

import year_2022.day_14.orientation.Coordinate
import year_2022.day_14.orientation.Direction

data class Cave(private val rockPaths: List<RockPath>) {
    private val rockCoordinates: Set<Coordinate> = rockCoordinatesOfPaths()
    private val caughtSandUnits = mutableListOf<Coordinate>()

    fun caughtSandUnits(): List<Coordinate> =
        caughtSandUnits

    fun maxAmountOfSandUnits(source: Coordinate): Int {
        dropSandUntilItFallsIndefinitely(source)
        return caughtSandUnits.size
    }

    private tailrec fun dropSandUntilItFallsIndefinitely(source: Coordinate) {
        val (before, after) = amountOfCaughtSandUnitsBeforeAndAfterDroppingSand(source)
        if (before < after) {
            dropSandUntilItFallsIndefinitely(source)
        }
    }

    private fun amountOfCaughtSandUnitsBeforeAndAfterDroppingSand(source: Coordinate): Pair<Int, Int> {
        val previousAmountOfSandUnits = caughtSandUnits.size
        dropSandUnitFrom(source)
        val currentAmountOfSandUnits = caughtSandUnits.size
        return previousAmountOfSandUnits to currentAmountOfSandUnits
    }

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

    private fun rockCoordinatesOfPaths(): Set<Coordinate> =
        rockPaths.flatMap(RockPath::rockCoordinatesOfPath)
            .toSet()
}