package com.bignerdranch.nyethack.locations

import com.bignerdranch.nyethack.*

class TownSquare : Room("The Town Square") {
    override val status = "Bustling";

    private val bellSound = "GWONG!"

    private val hatShop = DropOffBox<Hat>();
    private val gemstonesShop = DropOffBox<GemStones>();

    final override fun enterRoom() {
        for (i in 1..2) {
            ringBell();
        }
        narrate("The villagers rally and cheer as the hero enters");
    }

    fun ringBell() {
        narrate("The bell tower announces the hero's presence: $bellSound")
    }

    fun <T> sellItem(item: T): Int where T : Loot, T : Sellable {
        return when (item) {
            is Hat -> hatShop.sellLoot(item)
            is GemStones -> gemstonesShop.sellLoot(item)
            else -> 0
        }
    }
}