package com.bignerdranch.nyethack

import helpers.makeRed
import helpers.makeYellow

lateinit var player: Player;

fun main() {
    narrate("=== Welcom to NyetHack! ===", ::makeRed)

    val playerName = promptHeroName();
    player = Player(playerName);
    changeNarratorMood();

    Game.play();
}

private fun promptHeroName(): String {
    narrate("A hero enters the town of Kronstadt. What is their name?", ::makeYellow)

    val input = readlnOrNull();

    require(!input.isNullOrEmpty()) {
        "The hero must have a name."
    }

    return input;
}