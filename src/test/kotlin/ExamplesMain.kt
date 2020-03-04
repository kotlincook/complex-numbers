package org.kotlinmath

import java.util.*
import kotlin.math.PI

/**
 * A series of common usages are shown below
 */
fun main() {
    val z1 = 3 + 4.I
    val z2 = I * z1
    val z3 = "4+3i".toComplex()
    val z4 = I * z3 + exp(I * PI/2) + sqrt(-9) / ln(z2)
    val z5 = 1.5F / z4 * 2 - 4.32 + 10L + (3.toBigDecimal() * I)
    val thisIsTrue = 1 / ZERO == INF && ONE / 0 == INF && (1 / INF) == ZERO
    val z6 = I * z5 + exp(I * PI/2) + sqrt(-9)

    println(thisIsTrue)
    print(z6)
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