package utils.aoc

data class Exercise(val year: Int, val day: Int) {
    private val paddedDay: String = day.toString()
        .padStart(2, '0')

    fun fileFromResource(fileName: String): String =
        "./src/main/resources/year_$year/day_$paddedDay/$fileName"
}