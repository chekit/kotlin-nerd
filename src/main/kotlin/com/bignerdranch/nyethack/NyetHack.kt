package com.bignerdranch.nyethack

import helpers.makeRed
import helpers.makeYellow

lateinit var player: Player;

fun main() {
    narrate("=== Welcom to NyetHack! ===", ::makeRed)

    val playerName = promptHeroName();
    player = Player(playerName);
//    changeNarratorMood()

    // Test
    val lootBoxOne: LootBox<Fedora> = LootBox(Fedora("a generic-looking fedora", 15));
    val lootBoxTwo: LootBox<GemStones> = LootBox(GemStones(150))

    val gemsDropOffBox= DropOffBox<GemStones>();

    narrate("hero wants to sell gems and get ${lootBoxTwo.takeLoot()?.let { gemsDropOffBox.sellLoot(it) }}")

    repeat(2) {
        narrate(lootBoxOne.takeLoot()?.let {
            "The hero retrieves ${it.name} from the box"
        } ?: "The box is empty")
    }

    Game.play();
}

private fun promptHeroName(): String {
    narrate("A hero enters the town of Kronstadt. What is their name?", ::makeYellow)

//    val input = readlnOrNull();
//
//    require(!input.isNullOrEmpty()) {
//        "The hero must have a name."
//    }
//
//    return input;

    println("Madrigal");
    return "Madrigal";
}