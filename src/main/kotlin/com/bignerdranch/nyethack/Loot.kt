package com.bignerdranch.nyethack

abstract class Loot {
    abstract val name: String;
}

interface Sellable {
    val value: Int;
}

class LootBox<T: Loot>(private val contents: T) {
        var isOpen = false
        private set

    fun takeLoot(): T? {
        return contents
            .takeIf { !isOpen }
            .also { isOpen = true };
    }
}

class DropOffBox<T> where T: Loot, T: Sellable {
    fun sellLoot(sellableLoot: T): Int {
        return (sellableLoot.value * 0.7).toInt();
    }
}

data class Fedora(override val name: String, override val value: Int) : Loot(), Sellable

data class GemStones(override val value: Int): Loot(), Sellable {
    override val name = "sack of gemstones worth value $value gold"
}

data class Key(override val name: String): Loot()