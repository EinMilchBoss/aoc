package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CollectionKtTest {
    @Test
    fun testRelativeValue() {
        val list = List(10) { it }
        val array = arrayOf(0, 1, 2, 3, 4)

        Assertions.assertEquals(
            0,
            list.relativeValue(0)
        )

        Assertions.assertEquals(
            9,
            list.relativeValue(9)
        )

        Assertions.assertEquals(
            0,
            list.relativeValue(10)
        )

        Assertions.assertEquals(
            9,
            list.relativeValue(-1)
        )

        Assertions.assertEquals(
            0,
            array.relativeValue(5)
        )

        Assertions.assertEquals(
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

        Assertions.assertEquals(
            listOf(0, 1, 2, 3, 4),
            differentElementList
        )

        Assertions.assertEquals(
            listOf(5, 6, 7, 8, 9),
            differentListPopped
        )

        Assertions.assertEquals(
            listOf(0, 0, 0, 0, 0),
            sameElementList
        )

        Assertions.assertEquals(
            listOf(0, 0, 0, 0, 0),
            sameListPopped
        )
    }
}