package exercises

import kotlin.system.measureNanoTime

fun listVsSequence() {
    fun isPrime(num: Int) = num % 2 == 0;

    val listInNanos = measureNanoTime {
        val listOfPrimes = (0..7919)
            .toList()
            .filter { isPrime(it) }
            .take(1000);
    }

    val sequenceInNano = measureNanoTime {
        generateSequence(0) { it + 1 }
            .filter { isPrime(it) }
            .take(1000)
    }

    println(listInNanos);
    println("\n");
    println(sequenceInNano);
}
