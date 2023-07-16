package year_2022.day_14.aoc

import java.io.File

data class Exercise(val year: Int, val day: Int) {
    fun fileFromResource(fileName: String): String =
        "./src/main/resources/year_$year/day_$day/$fileName"
}

data class Inputs(private val exercise: Exercise) {
    val actual = Input.ACTUAL.readInput(exercise)
    val example = Input.EXAMPLE.readInput(exercise)
}

enum class Input(private val inputFileName: String) {
    ACTUAL("input.txt"),
    EXAMPLE("example.txt");

    fun readInput(exercise: Exercise): String =
        File(exercise.fileFromResource(inputFileName)).readAll()
}

private fun File.readAll(): String =
    readBytes().toString(Charsets.UTF_8)