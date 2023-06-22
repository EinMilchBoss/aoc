package year_2022.day_13

class UnevenBracketCountException(val context: String? = null, cause: Throwable? = null) : Exception(cause) {
    override val message = "Amount of opening and closing brackets is uneven."
}