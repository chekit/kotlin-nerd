package helpers

import com.bignerdranch.nyethack.Coordinate
import com.bignerdranch.nyethack.Room

fun locatePlayerPosition(worldMap: List<List<Room>>, currentPosition: Coordinate) {
    worldMap.forEachIndexed() { indexRow, row ->
        val rowAsString = List(row.size) { indexCol -> if (indexRow == currentPosition.y && indexCol == currentPosition.x) "X" else "0"}.joinToString(separator = " ")
        println(rowAsString)
    }
}