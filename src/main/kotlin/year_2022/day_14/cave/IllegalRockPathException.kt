package year_2022.day_14.cave

class IllegalRockPathException : Exception() {
    override val message = "Coordinates have to be different in order to have a valid rock path."
}