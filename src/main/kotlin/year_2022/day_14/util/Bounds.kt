package year_2022.day_14.util

data class Bounds(val left: Int, val right: Int) {
    val length = right - left + 1

    fun rangeInclusive(): IntRange =
        left..right
}