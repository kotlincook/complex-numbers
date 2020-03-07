package org.kotlinmath.examples

import org.kotlinmath.*
import java.lang.Math.PI
import kotlin.math.pow

fun zeta(s: Complex): Complex {
    var sum = ZERO
    for (n in 1..1000000) sum += 1 / pow(n, s)
    return sum
}

fun zeta(x: Number) = zeta(x.R)

fun main() {
    println(PI * PI / 6)
    println(zeta(2))

    println(PI.pow(6) / 945)
    println(zeta(6))
}

