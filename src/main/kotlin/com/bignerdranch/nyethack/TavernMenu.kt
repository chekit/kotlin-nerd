package com.bignerdranch.nyethack

import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

class TavernMenu(val tavernName: String) {
    private val menuData by lazy {
        File("data/tavern-menu-items.txt")
            .readText()
            .split("\n")
            .map { it.split(",").map { item -> item.trim() } };
    }

    val menuItems = menuData.associate { (_, name, price) ->
        name to price.toDouble()
    };

    val menuItemTypes = menuData.associate { (type, name) ->
        name to type
    };

    fun listTheMenu(): String {
        val items: List<List<String>> = menuData;
        val header = "*** Welcome to $tavernName ***";
        val menuGap = 3;

        val longestLine = items.fold(0) { acc, item ->
            val (_, name, price) = item;
            val currentItemLength = "$name,$price".length;

            if (currentItemLength >= acc) currentItemLength else acc;
        }

        val menuItemsMap = items.fold(HashMap<String, MutableList<String>>()) { acc, item ->
            val (group, name, price) = item;
            val rest = longestLine - "$name,$price".length;
            val dish = "$name${".".repeat(rest + menuGap)}$price"

            if (acc.containsKey(group)) {
                val gr = acc.getValue(group);
                gr.add(dish)
            } else {
                acc[group] = mutableListOf(dish)
            }

            acc
        }

        return "\n$header\n${
            menuItemsMap.entries.joinToString(separator = "\n", transform = { it ->
                val groupHeader = "~[${it.key}]~";
                val paddingLeft = (longestLine - groupHeader.length) / 2;
                val groupDishes = it.value.joinToString("\n");

                " ".repeat(paddingLeft) + groupHeader + "\n$groupDishes"
            })
        }\n"
    }

    fun getFavouriteItemByPatron(patron: String): List<String> {
        return when (patron) {
            "Alex Ironfoot" -> menuItems.keys.filter { menuItem ->
                menuItemTypes[menuItem]?.contains("desert") == true
            }

            else -> menuItems.keys.shuffled().take(Random.nextInt(1..2))
        }
    }
}