package year_2022.day_13.packet.exception

class UnevenBracketCountException(cause: Throwable? = null) : Exception(cause) {
    override val message = "Amount of opening and closing brackets is uneven."
}