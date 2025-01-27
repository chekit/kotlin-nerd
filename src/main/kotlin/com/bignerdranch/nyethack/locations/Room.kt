package com.bignerdranch.nyethack.locations

import com.bignerdranch.nyethack.Loot
import com.bignerdranch.nyethack.LootBox
import com.bignerdranch.nyethack.narrate

open class Room(val name: String) {
    protected open val status = "Calm";
    open val lootBox: LootBox<Loot> = LootBox.random()

    open fun description() = "$name (Currently: $status)";

    open fun enterRoom() {
        narrate("There is nothing to do here");
    }
}