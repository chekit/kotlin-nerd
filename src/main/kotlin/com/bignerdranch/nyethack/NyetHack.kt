package com.bignerdranch.nyethack

import helpers.makeRed
import helpers.makeYellow

lateinit var player: Player;

fun main() {
    narrate("Welcom to NyetHack!", ::makeRed)
    val playerName = promptHeroName();
    player = Player(playerName);
//    changeNarratorMood()

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

object Game {
    private val currentRoom = Tavern();

    init {
        narrate("Welcome, adventurer!")

        val mortality = if (player.isImmortal) "an immortal" else "a mortal";
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points");
    }

    fun play() {
        while (true) {
            narrate("${player.name} of ${player.hometown}, ${player.title}, is in ${currentRoom.description()}");
            currentRoom.enterRoom();

            print("> Enter your command: ");
            println("Last command: ${readLine()}");
        }
    }
}