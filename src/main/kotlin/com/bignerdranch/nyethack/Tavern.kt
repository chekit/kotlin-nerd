package com.bignerdranch.nyethack

import helpers.makeOrange

private const val TAVERN_MASTER = "Taernyl";
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

val names = setOf("Alex", "Eli", "Mordoc", "Sophie", "Tariq", "Bilbo");
val surnames = setOf("Ironfoot", "Fernsworth", "Baggins", "Dowstride");

class Tavern : Room(TAVERN_NAME) {
    val patrons: MutableSet<String> =
        names.shuffled().zip(surnames.shuffled()) { firstName, lastName -> "$firstName $lastName" }.toMutableSet();

    val patronsGold = mutableMapOf(
        TAVERN_MASTER to 86.00,
        player.name to 4.50,
        *patrons.map { it to 6.0 }.toTypedArray()
    )

    override val status = "busy";
    private val menu = Menu(TAVERN_NAME)

    val patronFavouriteItems = patrons.flatMap { menu.getFavouriteItemByPatron(it) };
    val itemOfTheDay = getItemOfDay(patronFavouriteItems)

    override fun enterRoom() {
        narrate("${player.name} enters $TAVERN_NAME");
        narrate("There are several items for sale");
        narrate(menu.listTheMenu());

        narrate("! The item of the day is the $itemOfTheDay !\n", ::makeOrange);

        narrate("${player.name} sees several patrons in tavern:");
        narrate(patrons.joinToString() + "\n");

        placeOrder(patrons.random(), menu.menuItems.keys.random())
    }

    private fun placeOrder(patronName: String, menuItemName: String) {
        val menuItemPrice = menu.menuItems.getValue(menuItemName);

        narrate("$patronName speaks with $TAVERN_MASTER to place an order");

        if (patronsGold.getOrDefault(patronName, 0.0) < menuItemPrice) {
            println("$TAVERN_MASTER says: '$patronName, you need more coin for $menuItemName\n");
            return;
        }

        val action = when (menu.menuItemTypes[menuItemName]) {
            "shandy", "elixir" -> "pours"
            "meal" -> "serves"
            else -> "hands"
        }
        narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
        narrate("$patronName pays $TAVERN_NAME $menuItemPrice gold\n")

        patronsGold[patronName] = patronsGold.getValue(patronName).minus(menuItemPrice);
        patronsGold[TAVERN_MASTER] = patronsGold.getValue(TAVERN_MASTER).plus(menuItemPrice);
    }

    private fun getItemOfDay(favourites: List<String>): String {
        val ratings = favourites.fold(mutableMapOf<String, Int>()) { acc, item ->
            if (acc.containsKey(item)) acc[item] = acc.getValue(item) + 1 else acc[item] = 1
            acc
        }
        val maxLikes = ratings.maxOf { it.value };
        return ratings.filter { it.value == maxLikes }.keys.random();
    }
}