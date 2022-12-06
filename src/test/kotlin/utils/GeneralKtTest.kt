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
            "Example failed. '4' expected, got '2'.",
            test(input, "4") { it.count().toString() }
        )
    }
}