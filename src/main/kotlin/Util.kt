fun test(input: List<String>, expected: String, solver: (List<String>) -> String): String =
    solver(input).let { actual ->
        if (actual != expected) "Example failed. $expected expected, got $actual."
        else "Example succeeded!"
    }

fun List<String>.lineSeparatedBlocks(condition: (String) -> Boolean): List<List<String>> {
    tailrec fun iterate(lineIndex: Int, blockIndex: Int, result: MutableList<MutableList<String>>): List<List<String>> {
        if (condition(this[lineIndex]))
            return iterate(lineIndex + 1, blockIndex + 1, result)

        if (result.lastIndex == blockIndex)
            result[blockIndex].add(this[lineIndex])
        else
            result.add(mutableListOf(this[lineIndex]))

        return if (lineIndex < this.lastIndex)
            iterate(lineIndex + 1, blockIndex, result)
        else
            result
    }
    return iterate(0, 0, mutableListOf())
}