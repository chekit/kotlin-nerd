package com.bignerdranch.nyethack

import LeetAlphabet
import kotlin.random.Random
import kotlin.random.nextInt

var narrationModifier: (String) -> String = { it };

fun changeNarratorMood() {
    val mood: String;
    val modifier: (String) -> String;

    when (Random.nextInt(1..8)) {
        1 -> {
            mood = "loud";
            modifier = { message ->
                val numExclamationPoints = 3;
                message.uppercase() + "!".repeat(numExclamationPoints);
            }
        }

        2 -> {
            mood = "tired";
            modifier = { message ->
                message.lowercase().replace(" ", "... ")
            }
        }

        3 -> {
            mood = "unsure";
            modifier = { message -> "$message?" }
        }

        4 -> {
            var narrationsGiven = 0;
            mood = "like sending an itemized bill";
            modifier = { message ->
                narrationsGiven++;
                "$message,\n (I have narrated $narrationsGiven things)"
            }
        }

        5 -> {
            mood = "lazy"
            modifier = { message -> "${message.split(' ').first()}... I'm $mood" }
        }

        6 -> {
            mood = "mysterious"
            val regex = "\\w".toRegex();
            modifier = { message -> message.replace(regex) { LeetAlphabet.getOrDefault(it.value.uppercase(), "-1") } }
        }

        7 -> {
            mood = "poetic"
            modifier = { message -> message.split(' ').joinToString("") { "\t".repeat(Random.nextInt(0..3)) + it + "\n" } }
        }

        else -> {
            mood = "professional";
            modifier = { message -> "$message." }
        }
    }

    narrate("The narrator begins to feel $mood", modifier);
}


inline fun narrate(
    message: String,
    modifier: (String) -> String = { narrationModifier(it) }
) {
    println(modifier(message))
}