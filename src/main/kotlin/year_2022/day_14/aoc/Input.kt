package year_2022.day_14.aoc

import java.io.File

enum class Input(private val inputFileName: String) {
    ACTUAL("input.txt"),
    EXAMPLE("example.txt");

    fun readInput(exercise: Exercise): String =
        File(exercise.fileFromResource(inputFileName)).readAll()
}

private fun File.readAll(): String =
    readBytes().toString(Charsets.UTF_8)