package year_2022.day_14.cave

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import year_2022.day_14.orientation.Coordinate
import kotlin.test.assertEquals

class CaveTest {
    private val origin = Coordinate(0, 0)

    private fun Cave.setOfCaughtSandUnits(): Set<Coordinate> =
        caughtSandCoordinates().toSet()

    private fun createCaveWithOneRockWall(from: Coordinate, to: Coordinate): Cave =
        Cave(listOf(RockPath(listOf(from, to))))

    @Test
    fun `dropSandUnitFrom gets caught by wall`() {
        val cave = createCaveWithOneRockWall(Coordinate(-1, 5), Coordinate(1, 5))

        cave.dropSandFromSource(origin)

        assertEquals(setOf(Coordinate(0, 4)), cave.setOfCaughtSandUnits())
    }

    @Test
    fun `dropSandUnitFrom moves diagonal if blocked`() {
        val cave = createCaveWithOneRockWall(Coordinate(-2, 5), Coordinate(2, 5))

        repeat(4) {
            cave.dropSandFromSource(origin)
        }

        assertEquals(
            setOf(Coordinate(0, 4), Coordinate(-1, 4), Coordinate(1, 4), Coordinate(0, 3)), cave.setOfCaughtSandUnits()
        )
    }

    @Test
    fun `dropSandUnitFrom moves diagonal if blocked more than once`() {
        val cave = createCaveWithOneRockWall(
            Coordinate(-3, 5), Coordinate(3, 5)
        )

        repeat(5) {
            cave.dropSandFromSource(origin)
        }

        assertEquals(
            setOf(Coordinate(0, 4), Coordinate(-1, 4), Coordinate(1, 4), Coordinate(0, 3), Coordinate(-2, 4)),
            cave.setOfCaughtSandUnits()
        )
    }

    @Test
    fun `dropSandUnitFrom moves diagonal if blocked more than once and piles up`() {
        val cave = createCaveWithOneRockWall(
            Coordinate(-3, 5), Coordinate(3, 5)
        )

        repeat(9) {
            cave.dropSandFromSource(origin)
        }

        assertEquals(
            setOf(
                Coordinate(0, 4),
                Coordinate(-1, 4),
                Coordinate(1, 4),
                Coordinate(0, 3),
                Coordinate(-2, 4),
                Coordinate(-1, 3),
                Coordinate(2, 4),
                Coordinate(1, 3),
                Coordinate(0, 2)
            ), cave.setOfCaughtSandUnits()
        )
    }

    @Test
    fun `dropSandUnitFrom recognizes sand units falling into the abyss`() {
        val cave = Cave(emptyList())
        val indefinitelyFallingSandUnit = { cave.dropSandFromSource(origin) }

        assertDoesNotThrow(indefinitelyFallingSandUnit)
    }

    @Test
    fun `dropSandUnitFrom does not drop sand if source is blocked`() {
        val cave = createCaveWithOneRockWall(
            Coordinate(-3, 3), Coordinate(3, 3)
        )

        repeat(10) {
            cave.dropSandFromSource(origin)
        }

        assertEquals(
            setOf(
                Coordinate(0, 2),
                Coordinate(-1, 2),
                Coordinate(1, 2),
                Coordinate(0, 1),
                Coordinate(-2, 2),
                Coordinate(-1, 1),
                Coordinate(2, 2),
                Coordinate(1, 1),
                Coordinate(0, 0)
            ), cave.setOfCaughtSandUnits()
        )
    }
}