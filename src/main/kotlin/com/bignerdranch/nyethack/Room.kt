package com.bignerdranch.nyethack

open class Room(val name: String) {
    protected open val status = "Calm";

    fun description() = "$name (Currently: $status)";

    open fun enterRoom() {
        narrate("There is nothing to do here");
    }
}

class TownSquare: Room("The Town Square") {
    override val status ="Bustling";

    private val bellSound ="GWONG!"

    final override fun enterRoom() {
        for (i in 1..2) {
            ringBell();
        }
        narrate("The villagers rally and cheer as the hero enters");
    }

    fun ringBell() {
        narrate("The bell tower announces the hero's presence: $bellSound")
    }
}