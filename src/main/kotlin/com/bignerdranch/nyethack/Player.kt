package com.bignerdranch.nyethack

class Player(
    initialName: String,
    val hometown: String = "Neversummer",
    override var healthPoints: Int,
    val isImmortal: Boolean
) : Fightable {
    override var name = initialName
        get() = field.replaceFirstChar { it.uppercase() }
        private set(value) {
            field = value.trim()
        }

    override val diceCount = 3;

    override val diceSides = 4;

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

    val prophecy by lazy {
        narrate("$name embarks on an arduous quest to locate a fortune teller");
        Thread.sleep(3000);
        narrate("The fortune teller bestows a prophecy upon $name");
        "An interpid hero from $hometown shall some day " + listOf(
            "form an unlikely bond between two warring factions",
            "take possession of an otherworldly blade",
            "bring the gift of creation back to the world",
            "best the world-eater"
        ).random();
    }

    val inventory = mutableListOf<Loot>()
    var gold = 0;

    init {
        require(healthPoints > 0) { "healthPoints must be greater than zero" };
        require(name.isNotBlank()) { "Player must have name" };
    }

    constructor(name: String): this (
        initialName = name,
        healthPoints = 100,
        isImmortal = false
    ) {
        if (name.equals("Jason", ignoreCase = true)) {
            healthPoints = 500;
        }
    }

    fun castFireball(numFireballs: Int = 2) {
        narrate("A glass of Fireball springs into existence (x$numFireballs)")
    }

    fun changeName(value: String) {
        narrate("$name legally changes their name to $value")
        name = value;
    }

    fun prophesize() {
        narrate("$name thinks about their future");
        narrate("A fortune teller told $name, \"$prophecy\"");
    }

    override fun takeDamage(damage: Int) {
       if (!isImmortal) {
           healthPoints -= damage;
       }
    }
}