package exercises


var gradesByStudent = mapOf("Josh" to 4.0, "Alex" to 2.0, "Jane" to 3.0);

fun <K, V> flipValues(map: Map<K, V>): Map<V, K> {
    return map.entries.toList().associate { (key, value) ->
        Pair(value, key);
    };
}

fun main() {
    println(gradesByStudent);
    val flipped = flipValues(gradesByStudent);
    println(flipped);
}