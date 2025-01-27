package com.bignerdranch.nyethack

import kotlin.random.Random
import kotlin.random.nextInt

abstract class Loot {
    abstract val name: String;
}

interface Sellable {
    val value: Int;
}

abstract class Hat : Loot(), Sellable

class LootBox<out T : Loot>(private val contents: T) {
    var isOpen = false
        private set

    fun takeLoot(): T? {
        return contents
            .takeIf { !isOpen }
            .also { isOpen = true };
    }

    companion object {
        fun random(): LootBox<Loot> = LootBox(
            contents = when (Random.nextInt(1..100)) {
                in 1..5 -> Fez("fez of immaculate style", 150)
                in 6..10 -> Fedora("fedora of knowledge", 125)
                in 11..15 -> Fedora("stunning teal fedora", 75)
                in 16..30 -> Fez("ordinary fez", 15)
                in 31..50 -> Fedora("ordinary fedora", 10)
                else -> GemStones(Random.nextInt(50..100))
            }
        )
    }
}

class DropOffBox<in T> where T : Loot, T : Sellable {
    fun sellLoot(sellableLoot: T): Int {
        return (sellableLoot.value * 0.7).toInt();
    }
}

data class Fedora(override val name: String, override val value: Int) : Hat()
data class Fez(override val name: String, override val value: Int) : Hat()

data class GemStones(override val value: Int) : Loot(), Sellable {
    override val name = "sack of gemstones worth value $value gold"
}

data class Key(override val name: String) : Loot()