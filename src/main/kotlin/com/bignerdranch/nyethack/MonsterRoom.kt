package com.bignerdranch.nyethack

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