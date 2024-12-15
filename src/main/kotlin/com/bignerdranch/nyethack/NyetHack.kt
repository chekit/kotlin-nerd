package com.bignerdranch.nyethack

import helpers.makeRed
import helpers.makeYellow

lateinit var player: Player;

fun main() {
    narrate("Welcom to NyetHack!", ::makeRed)
    val playerName = promptHeroName();
    player = Player(playerName);
    player.prophesize();
//    changeNarratorMood()

    val currentRoom = Tavern();
    val mortality = if (player.isImmortal) "an immortal" else "a mortal";
    narrate("${player.name} of ${player.hometown}, ${player.title}, is in ${currentRoom.description()}");
    narrate("${player.name}, $mortality, has ${player.healthPoints} health points");
    currentRoom.enterRoom()

//    visitTavern();
    player.castFireball();
    player.prophesize();

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