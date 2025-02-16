package com.bignerdranch.nyethack

data class Coordinate(val x: Int, val y: Int) {
    operator fun plus(other: Coordinate) = Coordinate(other.x + x, other.y + y)
}

enum class Direction(private val directionCoordinate: Coordinate) {
    NORTH(Coordinate(0, -1)),
    EAST(Coordinate(1, 0)),
    SOUTH(Coordinate(0, 1)),
    WEST(Coordinate(-1, 0));

    fun updateCoordinate(coordinate: Coordinate) = coordinate + directionCoordinate;
}