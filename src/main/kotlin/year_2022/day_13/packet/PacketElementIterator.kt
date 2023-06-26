package year_2022.day_13.packet

fun String.packetElementIterator(): PacketElementIterator =
    PacketElementIterator(this)

class PacketElementIterator(content: String) : Iterator<PacketElement> {
    private val unwrappedContent: String
    private var nextStartIndex = 0

    init {
        unwrappedContent = content.unwrapOuterBrackets()
    }

    companion object {
        private const val COMMA_OFFSET = 1
    }

    override fun hasNext(): Boolean =
        nextStartIndex < unwrappedContent.length

    override fun next(): PacketElement {
        val content = relevantContentWindow()
        val packetElement = content.readNextElement()
        updateNextStartIndex(packetElement.content.length)
        return packetElement
    }

    private fun relevantContentWindow(): String =
        unwrappedContent.substring(nextStartIndex)

    private fun updateNextStartIndex(lengthOfNextPacketValue: Int) {
        nextStartIndex += lengthOfNextPacketValue + COMMA_OFFSET
    }
}

private fun String.unwrapOuterBrackets(): String =
    if (startsWith('[') && endsWith(']')) substring(1, lastIndex)
    else throw UnevenBracketCountException()

private fun String.readNextElement(): PacketElement =
    if (isPacketList()) {
        val indexOfClosingBracket = findIndexOfClosingBracket()
        PacketElement.List(extractInnerList(indexOfClosingBracket))
    } else {
        PacketElement.Value(readUntilNextComma())
    }

private fun String.findIndexOfClosingBracket(): Int {
    var bracketCounter = 0
    for ((index, char) in this.withIndex()) {
        when (char) {
            '[' -> bracketCounter++
            ']' -> bracketCounter--
        }

        if (bracketCounter == 0) {
            return index
        }
    }
    throw UnevenBracketCountException()
}

private fun String.isPacketList(): Boolean =
    startsWith('[')

private fun String.extractInnerList(indexOfClosingBracket: Int): String {
    val endIndexInclusive = indexOfClosingBracket + 1
    return substring(0, endIndexInclusive)
}

private fun String.readUntilNextComma() =
    takeWhile { it != ',' }