import java.io.File

private const val TAVERN_MASTER = "Taernyl";
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

val names = setOf("Alex", "Eli", "Mordoc", "Sophie", "Tariq", "Bilbo");
val surnames = setOf("Ironfoot", "Fernsworth", "Baggins", "Dowstride");

private val menuData = File("data/tavern-menu-items.txt")
    .readText()
    .split("\n");

private val menuItems = List(menuData.size) { index ->
    val (_, name, price) = menuData[index].split(",").map { it.trim() }
    name to price.toDouble()
}.toMap()

private val menuItemTypes = List(menuData.size) { index ->
    val (type, name) = menuData[index].split(",").map { it.trim() }
    name to type
}.toMap()


fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME");
    narrate("There are several items for sale");
    narrate(listTheMenu(menuData));

    val patrons: MutableSet<String> = mutableSetOf();
    val patronsGold = mutableMapOf(
        TAVERN_MASTER to 86.00,
        heroName to 4.50
    )
    while (patrons.size < 10) {
        val patronName = "${names.random()} ${surnames.random()}";

        patrons += patronName;
        patronsGold += patronName to 6.0;
    }

    narrate("$heroName sees several patrons in tavern:");
    narrate(patrons.joinToString());

    repeat(3) {
        placeOrder(patrons.random(), menuItems.keys.random(), patronsGold)
    }
    displayPatronBalances(patronsGold);
}

fun placeOrder(patronName: String, menuItemName: String, patronsGold: MutableMap<String, Double>) {
    val menuItemPrice = menuItems.getValue(menuItemName);

    narrate("$patronName speaks with $TAVERN_MASTER to place an order");

    if (patronsGold.getOrDefault(patronName, 0.0) < menuItemPrice) {
        println("$TAVERN_MASTER says: '$patronName, you need more coin for $menuItemName");
        return;
    }

    val action = when (menuItemTypes[menuItemName]) {
        "shandy", "elixir" -> "pours"
        "meal" -> "serves"
        else -> "hands"
    }
    narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
    narrate("$patronName pays $TAVERN_NAME $menuItemPrice gold")

    patronsGold[patronName] = patronsGold.getValue(patronName).minus(menuItemPrice);
    patronsGold[TAVERN_MASTER] = patronsGold.getValue(TAVERN_MASTER).plus(menuItemPrice);
}

fun listTheMenu(items: List<String>): String {
    val header = "*** Welcome to $TAVERN_NAME ***";
    val menuGap = 3;
    var longestLine = 0;
    val menuItemsMap = HashMap<String, MutableList<String>>();

    items
        .forEach {
            val (_, name, price) = it.split(",");
            val actualLength = "$name,$price".length;

            if (actualLength >= longestLine) {
                longestLine = actualLength;
            }
        }

    items
        .forEach {
            val (group, name, price) = it.split(",").map { it.trim() }

            val rest = longestLine - "$name,$price".length;
            val dish = "$name${".".repeat(rest + menuGap)}$price"

            if (menuItemsMap.containsKey(group)) {
                val gr = menuItemsMap.getValue(group);
                gr.add(dish)
            } else {
                menuItemsMap[group] = mutableListOf(dish)
            }
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

fun displayPatronBalances(patronsGold:  Map<String, Double>) {
    patronsGold.forEach { (patron, balance) ->
        println("$patron left ${"%.2f".format(balance)} gold")
    }
}