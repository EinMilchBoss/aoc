package utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GeneralKtTest {
    @Test
    fun testTest() {
        val input = listOf(
            "Hello",
            "World"
        )

        assertEquals(
            "Example succeeded!",
            test(input, "2") { it.count().toString() }
        )

        assertEquals(
            "Example failed. 4 expected, got 2.",
            test(input, "4") { it.count().toString() }
        )
    }

    @Test
    fun testLineSeparatedBlocks() {
        val input = listOf(
            "Hello",
            "World",
            "\n",
            "Today",
            "Is",
            "Beautiful"
        )

        assertEquals(
            listOf(
                listOf(
                    "Hello",
                    "World"
                ),
                listOf(
                    "Today",
                    "Is",
                    "Beautiful"
                )
            ),
            input.lineSeparatedBlocks { it == "\n" }
        )
    }

    @Test
    fun testRelativeValue() {
        val list = List(10) { it }
        val array = arrayOf(0, 1, 2, 3, 4)

        assertEquals(
            0,
            list.relativeValue(0)
        )

        assertEquals(
            9,
            list.relativeValue(9)
        )

        assertEquals(
            0,
            list.relativeValue(10)
        )

        assertEquals(
            9,
            list.relativeValue(-1)
        )

        assertEquals(
            0,
            array.relativeValue(5)
        )

        assertEquals(
            4,
            array.relativeValue(-1)
        )
    }

    @Test
    fun testPop() {
        val differentElementList = MutableList(10) { it }
        val sameElementList = MutableList(10) { 0 }

        val differentListPopped = differentElementList.pop(5)
        val sameListPopped = sameElementList.pop(5)

        assertEquals(
            listOf(0, 1, 2, 3, 4),
            differentElementList
        )

        assertEquals(
            listOf(5, 6, 7, 8, 9),
            differentListPopped
        )

        assertEquals(
            listOf(0, 0, 0, 0, 0),
            sameElementList
        )

        assertEquals(
            listOf(0, 0, 0, 0, 0),
            sameListPopped
        )
    }
}