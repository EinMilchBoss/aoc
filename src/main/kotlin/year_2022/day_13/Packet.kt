package year_2022.day_13

data class Packet(val data: ValueList) {
    sealed class Data
    data class Value(val value: Int) : Data()
    data class ValueList(val values: List<Data>) : Data()
}

fun List<String>.parsePacketPairs(): List<Pair<Packet, Packet>> {
    return joinToString(System.lineSeparator())
        .split(getEmptyLineSeparator())
        .map { packetPairContent ->
            packetPairContent.lines()
                .map { Packet(it.parseValueList()) }
                .zipWithNext()
                .first()
        }
}

fun getEmptyLineSeparator(): String = List(2) { System.lineSeparator() }.joinToString("")

fun String.parseValueList(): Packet.ValueList {
    val values = mutableListOf<Packet.Data>()
    for (packetValue in packetElementIterator()) {
        val slice = packetValue.get()
        when (packetValue) {
            is ValueElement -> values.add(slice.tryParsePacketValue())
            is ValueListElement -> values.add(slice.parseValueList())
        }
    }
    return Packet.ValueList(values)
}

fun String.tryParsePacketValue(): Packet.Value {
    return try {
        Packet.Value(toInt())
    } catch (exception: NumberFormatException) {
        throw UnevenBracketCountException(this, exception)
    }
}