package year_2022.day_14.cave

import year_2022.day_14.orientation.Coordinate
import year_2022.day_14.util.toPair

data class RockPath(val coordinatesOfEnds: List<Coordinate>) {
    fun rockCoordinatesOfPath(): List<Coordinate> {
        val remainingCoordinates = coordinatesOfEnds.windowed(2)
            .map(List<Coordinate>::toPair)
            .flatMap(Pair<Coordinate, Coordinate>::allCoordinates)

        return listOf(coordinatesOfEnds.first()) + remainingCoordinates
    }
}

private fun Pair<Coordinate, Coordinate>.allCoordinates(): List<Coordinate> =
    let { (start, end) ->
        val direction = start.directionTo(end)
        List(start.manhattanDistanceTo(end)) { start + direction.step.scale(it + 1) }
    }