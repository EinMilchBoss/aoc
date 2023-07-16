package year_2022.day_14

fun <T> List<T>.toPair(): Pair<T, T> =
    zipWithNext().first()