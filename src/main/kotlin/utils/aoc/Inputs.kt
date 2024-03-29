package utils.aoc

data class Inputs(private val exercise: Exercise) {
    val actual = Input.ACTUAL.readInput(exercise)
    val example = Input.EXAMPLE.readInput(exercise)
}