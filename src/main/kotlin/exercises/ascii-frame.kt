package exercises

fun frame(name: String, padding: Int, formatChar: String = "*"): String {
    val greeting = "$name!";
    val middle = formatChar
        .padEnd(padding)
        .plus(greeting)
        .plus(formatChar.padStart(padding));
    val borderTB = middle.indices.joinToString("") { formatChar }
    return "$borderTB\n$middle\n$borderTB"
}

fun String.frame(padding: Int) = frame(this, padding);

fun main() {
    println("Welcome, Madrigal".frame(5))
}