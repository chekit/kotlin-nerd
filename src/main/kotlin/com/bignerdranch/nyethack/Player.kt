package com.bignerdranch.nyethack

class Player(
    initialName: String,
    hometown: String,
    healthPoints: Int,
    isImmortal: Boolean
) {
    var name = initialName
        get() = field.replaceFirstChar { it.uppercase() }
        private set
//        private set(value) {
//            field = value.trim()
//        }

    val hometown = hometown;
    val healthPoints = healthPoints;
    val isImmortal =  isImmortal;

    val title: String
    get() = when {
        name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowel"
        name.all { it.isDigit() } -> "The Identifiable"
        name.none { it.isLetter() } -> "The Witness Protection Member"
        name.all { it.isUpperCase() } -> "Legendary"
        name.length > 10 -> "Spacious"
        name.lowercase() == name.lowercase().reversed() -> "Palindrome"
        else -> "The Renowned Hero"
    }

    fun castFireball(numFireballs: Int = 2) {
        narrate("A glass of Fireball springs into existence (x$numFireballs)")
    }

    fun changeName(value: String) {
        narrate("$name legally changes their name to $value")
        name = value;
    }
}