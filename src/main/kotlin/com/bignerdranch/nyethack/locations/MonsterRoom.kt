package com.bignerdranch.nyethack.locations

import com.bignerdranch.nyethack.Goblin
import com.bignerdranch.nyethack.Monster
import com.bignerdranch.nyethack.narrate

class MonsterRoom(
    name: String,
    var monster: Monster? = Goblin()
) : Room(name) {
    override fun description() = super.description() + " (Creature:  ${monster?.description ?: "None"})";

    override fun enterRoom() {
        if (monster == null) {
            super.enterRoom()

        } else {
            narrate("Danger is lurking in this room")
        }
    }
}