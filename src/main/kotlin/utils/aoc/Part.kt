package utils.aoc

class Part private constructor(
    private val partNumber: Int,
    private val inputs: Inputs,
    private val algorithm: String.() -> String,
) {
    companion object {
        fun one(inputs: Inputs, algorithm: String.() -> String): Part {
            return Part(1, inputs, algorithm)
        }

        fun two(inputs: Inputs, algorithm: String.() -> String): Part {
            return Part(2, inputs, algorithm)
        }
    }

    fun run(): String =
        inputs.actual.algorithm()

    fun testProtocol(expected: String): String {
        val algorithmResult = resultOfAlgorithmWithExampleInput()
        val testResult = testResult(expected, algorithmResult)
        return arrayOf(
            "--- PART $partNumber ---",
            "Expected:\n$expected",
            "Actual:\n$algorithmResult",
            "--- $testResult ---\n"
        ).joinToString("\n")
    }

    private fun resultOfAlgorithmWithExampleInput(): String =
        inputs.example.algorithm()

    private fun testResult(expected: String, actual: String): String =
        if (expected == actual) "SUCCEEDED" else "FAILED"
}