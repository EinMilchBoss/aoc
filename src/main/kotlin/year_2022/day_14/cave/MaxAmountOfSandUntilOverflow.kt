package year_2022.day_14.cave

import year_2022.day_14.orientation.Coordinate

fun Cave.maxAmountOfSandUntilOverflow(source: Coordinate): Int {
    dropSandUntilItFallsIndefinitely(source)
    return caughtSandUnits.size
}

tailrec fun Cave.dropSandUntilItFallsIndefinitely(source: Coordinate) {
    val (before, after) = amountOfCaughtSandUnitsBeforeAndAfterDroppingSand(source)
    if (before < after) {
        dropSandUntilItFallsIndefinitely(source)
    }
}

private fun Cave.amountOfCaughtSandUnitsBeforeAndAfterDroppingSand(source: Coordinate): Pair<Int, Int> {
    val previousAmountOfSandUnits = caughtSandUnits.size
    dropSandUnitFrom(source)
    val currentAmountOfSandUnits = caughtSandUnits.size
    return previousAmountOfSandUnits to currentAmountOfSandUnits
}