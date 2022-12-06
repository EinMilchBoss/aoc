package utils

fun test(input: List<String>, expected: String, solver: (List<String>) -> String): String =
    solver(input).let { actual ->
        if (actual != expected) "Example failed. '$expected' expected, got '$actual'."
        else "Example succeeded!"
    }