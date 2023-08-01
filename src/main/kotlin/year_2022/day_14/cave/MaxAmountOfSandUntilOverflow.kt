package year_2022.day_14.cave

import utils.orientation.Coordinate

fun Cave.maxAmountOfSandUntilOverflow(source: Coordinate): Int {
    dropSandUntilNothingChanges(source)
    return caughtSandCoordinates.size
}

