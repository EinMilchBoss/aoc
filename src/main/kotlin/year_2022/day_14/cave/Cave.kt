package year_2022.day_14.cave

import utils.orientation.Coordinate
import utils.orientation.Direction
import year_2022.day_14.cave.path.RockPath

class Cave(rockPaths: List<RockPath>) {
    internal val rockCoordinates: MutableSet<Coordinate>
    internal val caughtSandCoordinates: MutableList<Coordinate> = mutableListOf()

    init {
        rockCoordinates = rockPaths.flatMap(RockPath::rockCoordinatesOfPath)
            .toMutableSet()
    }

    fun caughtSandCoordinates(): List<Coordinate> =
        caughtSandCoordinates

    tailrec fun dropSandUntilNothingChanges(source: Coordinate) {
        val (before, after) = amountOfCaughtSandBeforeAndAfterDroppingSand(source)
        if (before < after) {
            dropSandUntilNothingChanges(source)
        }
    }

    private fun amountOfCaughtSandBeforeAndAfterDroppingSand(source: Coordinate): Pair<Int, Int> {
        val previousAmountOfSandUnits = caughtSandCoordinates.size
        dropSandFromSource(source)
        val currentAmountOfSandUnits = caughtSandCoordinates.size
        return previousAmountOfSandUnits to currentAmountOfSandUnits
    }

    fun dropSandFromSource(source: Coordinate) {
        if (!source.isBlocked()) moveSandVertically(source)
    }

    private tailrec fun moveSandVertically(current: Coordinate) {
        if (current.isLowerThanLowestRockWall()) return

        val fallenSand = current + Direction.DOWN.step
        if (fallenSand.isBlocked()) {
            moveSandDiagonally(current)
        } else {
            moveSandVertically(fallenSand)
        }
    }

    private fun moveSandDiagonally(current: Coordinate) {
        val leftDiagonal = current + Direction.DOWN.step + Direction.LEFT.step
        val rightDiagonal = current + Direction.DOWN.step + Direction.RIGHT.step
        when {
            !leftDiagonal.isBlocked() -> moveSandVertically(leftDiagonal)
            !rightDiagonal.isBlocked() -> moveSandVertically(rightDiagonal)
            else -> caughtSandCoordinates.add(current)
        }
    }

    private fun Coordinate.isLowerThanLowestRockWall(): Boolean =
        y >= lowestYOfRockWall()

    fun lowestYOfRockWall(): Int =
        try {
            rockCoordinates.maxOf { (_, y) -> y }
        } catch (_: NoSuchElementException) {
            0
        }

    private fun Coordinate.isBlocked(): Boolean =
        this in this@Cave.rockCoordinates || this in this@Cave.caughtSandCoordinates
}