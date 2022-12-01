import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class UtilKtTest {
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
}