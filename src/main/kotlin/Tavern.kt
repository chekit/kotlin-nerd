import helpers.makeOrange
import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

private const val TAVERN_MASTER = "Taernyl";
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

val names = setOf("Alex", "Eli", "Mordoc", "Sophie", "Tariq", "Bilbo");
val surnames = setOf("Ironfoot", "Fernsworth", "Baggins", "Dowstride");

private val menuData = File("data/tavern-menu-items.txt")
    .readText()
    .split("\n")
    .map { it.split(",").map { item -> item.trim() } };

private val menuItems = menuData.associate { (_, name, price) ->
    name to price.toDouble()
};

private val menuItemTypes = menuData.associate { (type, name) ->
    name to type
};

fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME");
    narrate("There are several items for sale");
    narrate(listTheMenu(menuData));

    val patrons: MutableSet<String> =
        names.shuffled().zip(surnames.shuffled()) { firstName, lastName -> "$firstName $lastName" }.toMutableSet();
    val patronsGold = mutableMapOf(
        TAVERN_MASTER to 86.00,
        heroName to 4.50,
        *patrons.map { it to 6.0 }.toTypedArray()
    )

    narrate("$heroName sees several patrons in tavern:");
    narrate(patrons.joinToString() + "\n");

    // ? How can we make it in one line?
    val patronFavouriteItems = patrons.flatMap { getFavouritePatronMenuItem(it) };

    narrate("! The item of the day is the ${getItemOfDay(patronFavouriteItems)} !\n", ::makeOrange);

    repeat(3) {
        placeOrder(patrons.random(), menuItems.keys.random(), patronsGold)
    }

    displayPatronBalances(patronsGold);

    patrons.filter { patronsGold.getOrDefault(it, 0.0) < 4.0 }.toSet()
        .also {
            patrons -= it;
            patronsGold -= it;
        }
        .forEach {
            narrate("$heroName sees $it departing the tavern")
        };

    narrate("There are still some patrons in the tavern");
    narrate(patrons.joinToString());
}

private fun getFavouritePatronMenuItem(patron: String): List<String> {
    return when (patron) {
        "Alex Ironfoot" -> menuItems.keys.filter { menuItem ->
            menuItemTypes[menuItem]?.contains("desert") == true
        }

        else -> menuItems.keys.shuffled().take(Random.nextInt(1..2))
    }
}

private fun getItemOfDay(favourites: List<String>): String {
    val ratings = favourites.fold(mutableMapOf<String, Int>()) { acc, item ->
        if (acc.containsKey(item)) acc[item] = acc.getValue(item) + 1 else acc[item] = 1
        acc
    }
    val maxLikes = ratings.maxOf { it.value };
    return ratings.filter { it.value == maxLikes }.keys.random();
}

private fun placeOrder(patronName: String, menuItemName: String, patronsGold: MutableMap<String, Double>) {
    val menuItemPrice = menuItems.getValue(menuItemName);

    narrate("$patronName speaks with $TAVERN_MASTER to place an order");

    if (patronsGold.getOrDefault(patronName, 0.0) < menuItemPrice) {
        println("$TAVERN_MASTER says: '$patronName, you need more coin for $menuItemName\n");
        return;
    }

    val action = when (menuItemTypes[menuItemName]) {
        "shandy", "elixir" -> "pours"
        "meal" -> "serves"
        else -> "hands"
    }
    narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
    narrate("$patronName pays $TAVERN_NAME $menuItemPrice gold\n")

    patronsGold[patronName] = patronsGold.getValue(patronName).minus(menuItemPrice);
    patronsGold[TAVERN_MASTER] = patronsGold.getValue(TAVERN_MASTER).plus(menuItemPrice);
}

fun listTheMenu(items: List<List<String>>): String {
    val header = "*** Welcome to $TAVERN_NAME ***";
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

fun displayPatronBalances(patronsGold: Map<String, Double>) {
    println("*".repeat(10));
    patronsGold.forEach { (patron, balance) ->
        println("$patron left ${"%.2f".format(balance)} gold")
    }
    println("${"*".repeat(10)}\n");
}