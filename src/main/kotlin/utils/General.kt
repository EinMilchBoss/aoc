package  utils

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

fun <T> List<T>.relativeValue(index: Int): T =
    if (index in indices)
        this[index]
    else
        if (index < 0) this[index + size]
        else this[index - size]

fun <T> Array<T>.relativeValue(index: Int): T =
    if (index in indices)
        this[index]
    else
        if (index < 0) this[index + size]
        else this[index - size]

// 12345 pop 3
// return 345
// list = 12

fun <T> MutableList<T>.pop(n: Int): List<T> {
    return this.takeLast(n).also {
        repeat(n) { this.removeLast() }
    }
}