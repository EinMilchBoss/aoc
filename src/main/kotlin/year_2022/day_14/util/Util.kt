package year_2022.day_14.util

fun <T> List<T>.toPair(): Pair<T, T> =
    zipWithNext().first()