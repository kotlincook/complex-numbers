package org.kotlinmath.examples

import org.kotlinmath.*
import kotlin.system.measureTimeMillis


fun main() {
    val z = complex(2, 1)
    var w = ZERO
    val time = measureTimeMillis {
        repeat(10000000) {
            w += z
            w *= z
            w -= z
            w /= z
        }
    }
    println("Result: $w")
    println("Rounding error: ${(10000000 + 10000000.I - w).mod}")
    println("Execution time: $time msec")
}

