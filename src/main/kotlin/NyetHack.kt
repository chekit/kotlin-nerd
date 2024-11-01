import helpers.makeYellow

var heroName = "";

fun main() {
    narrate("A hero enters the town of Kronstadt. What is their name?", ::makeYellow)

    heroName = promptHeroName();

//    changeNarratorMood()
    narrate("$heroName, ${createTitle(heroName)}, heads to the town square");
    visitTavern();
}



//fun makeYellow(message: String): String {
//    return "\u001b[33;1m$message\u001b[0m"
//};

private fun createTitle(name: String): String {
    return when {
        name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowel"
        name.all { it.isDigit() } -> "The Identifiable"
        name.none { it.isLetter() } -> "The Witness Protection Member"
        name.all { it.isUpperCase() } -> "Legendary"
        name.length > 10 -> "Spacious"
        name.lowercase() == name.lowercase().reversed() -> "Palindrome"
        else -> "The Renowned Hero"
    }
}

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