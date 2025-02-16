package com.bignerdranch.nyethack

import com.bignerdranch.nyethack.locations.Room

fun String.addEnthusiasm(enthusiasmLevel: Int = 1) = this + "!".repeat(enthusiasmLevel)

val String.numVowels
    get() = count { it.lowercase() in "aeiou" }

fun <T> T.print(): T {
    println(this)
    return this;
}

operator fun List<List<Room>>.get(coordinate: Coordinate) = this.getOrNull(coordinate.y)?.getOrNull(coordinate.x)

infix fun Coordinate.move(direction: Direction) = direction.updateCoordinate(this)

fun Room?.orEmptyRoom(name: String = "the middle of nowhere"): Room = this ?: Room(name)

fun String.frame(padding: Int = 5, delimiter: String = "*") = run {
    val middle = delimiter
        .padEnd(padding)
        .plus(this)
        .plus(delimiter.padStart(padding));
    val borderTB = middle.indices.joinToString("") { delimiter }
    "$borderTB\n$middle\n$borderTB"
}
