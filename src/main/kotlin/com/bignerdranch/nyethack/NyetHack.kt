package com.bignerdranch.nyethack

import helpers.makeYellow

val player = Player("Jason", "Jacksonville", 100, false);

fun main() {
//    narrate("A hero enters the town of Kronstadt. What is their name?", ::makeYellow)

//    changeNarratorMood()
    val mortality = if (player.isImmortal) "an immortal" else "a mortal";
    narrate("${player.name} of ${player.hometown}, ${player.title}, heads to the town square");
    narrate("${player.name}, $mortality, has ${player.healthPoints} health points");

    visitTavern();
    player.castFireball();
}



//fun makeYellow(message: String): String {
//    return "\u001b[33;1m$message\u001b[0m"
//};

private fun promptHeroName(): String {
    val result = "Madrigal";
//    val result = readlnOrNull();
//
//    require(!result.isNullOrEmpty()) {
//        "The hero must have a name."
//    }

    println(result);
    return result ?: "";
}