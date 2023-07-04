package year_2022.day_13.packet.exception

class IdenticalPacketsException : Exception() {
    override val message = "Packets are identical although they have to be different in order to compare them."
}