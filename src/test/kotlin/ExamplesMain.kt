package org.kotlinmath

import java.util.*
import kotlin.math.PI

fun main(args: Array<String>) {
    val z = 3 + 4.I
    val w = I * z
    val z3 = "4+3i".toComplex()
    val u = I * z + exp(I * PI/2) + sqrt(-9)
    val w1 = 1.5F / z * 2 - 4.32 + 10L + (3.toBigDecimal() * I)
    val thisIsTrue = 1 / ZERO == INF && ONE / 0 == INF && (1 / INF) == ZERO
    val w2 = I * z + exp(I * PI/2) + sqrt(-9)

    println(3.0 + NaN)
    println(complex(1.2, 3.4) == complex(1.2, 3.4))
    println(complex(-0.0, -0.0).equals(complex(0.0, 0.0)))
    println(-0.0 == 0.0)
    println(-0.0 - 0.0)
    println(complex(2.0, -2.0))
    println(exp(PI / 2 * I).asString(locale = Locale.US))
    println(cos(ZERO))
    println(sin(PI / 2 * ONE))
    println(2 * ONE / 2.I)
    println(exp(2.I))
    println(sqrt(1))
    println(exp(ln(2.I)))
}