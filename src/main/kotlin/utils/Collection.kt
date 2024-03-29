package utils

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

fun <T> MutableList<T>.pop(n: Int): List<T> {
    return takeLast(n).also {
        repeat(n) { removeLast() }
    }
}

fun <T> List<T>.toPair(): Pair<T, T> =
    zipWithNext().first()