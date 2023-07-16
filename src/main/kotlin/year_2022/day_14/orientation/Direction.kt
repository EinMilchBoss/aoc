package year_2022.day_14.orientation

enum class Direction(val step: Coordinate) {
    DOWN(Coordinate(0, 1)),
    UP(Coordinate(0, -1)),
    RIGHT(Coordinate(1, 0)),
    LEFT(Coordinate(-1, 0))
}