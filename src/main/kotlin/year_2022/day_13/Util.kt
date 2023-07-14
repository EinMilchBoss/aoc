package year_2022.day_13

fun <T> List<T>.toPair(): Pair<T, T> =
    zipWithNext().first()