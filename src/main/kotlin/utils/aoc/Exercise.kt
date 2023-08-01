package utils.aoc

data class Exercise(val year: Int, val day: Int) {
    fun fileFromResource(fileName: String): String =
        "./src/main/resources/year_$year/day_$day/$fileName"
}