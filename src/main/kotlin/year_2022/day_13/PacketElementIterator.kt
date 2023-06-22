package year_2022.day_13

fun String.packetElementIterator(): PacketElementIterator = PacketElementIterator(this)

sealed class PacketElement(private val slice: String) {
    fun get(): String = slice
}

class ValueListElement(slice: String) : PacketElement(slice)

class ValueElement(slice: String) : PacketElement(slice)

class PacketElementIterator(content: String) : Iterator<PacketElement> {
    private val unwrappedContent: String
    private val commaOffset = 1
    private var nextStartIndex = 0

    init {
        unwrappedContent = content.unwrapOuterBrackets()
    }

    private fun String.unwrapOuterBrackets(): String {
        return if (startsWith('[') && endsWith(']')) substring(1, lastIndex)
        else throw UnevenBracketCountException(this)
    }

    override fun hasNext(): Boolean = nextStartIndex < unwrappedContent.length

    override fun next(): PacketElement {
        val content = relevantContentWindow()
        val nextItem = content.getNextElement()
        updateNextStartIndex(nextItem.get().length)
        return nextItem
    }

    private fun String.getNextElement(): PacketElement {
        return if (isPacketList()) {
            val endIndex = findIndexOfClosingBracket()
            ValueListElement(extractInnerList(endIndex))
        } else {
            ValueElement(readUntilNextComma())
        }
    }

    private fun relevantContentWindow(): String = unwrappedContent.substring(nextStartIndex)

    private fun String.isPacketList(): Boolean = startsWith('[')

    private fun String.extractInnerList(indexOfClosingBrackets: Int): String {
        val endIndexInclusive = indexOfClosingBrackets + 1
        return substring(0, endIndexInclusive)
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
        throw UnevenBracketCountException(this)
    }

    private fun String.readUntilNextComma() = takeWhile { it != ',' }

    private fun updateNextStartIndex(lengthOfNextPacketValue: Int) {
        nextStartIndex += lengthOfNextPacketValue + commaOffset
    }
}