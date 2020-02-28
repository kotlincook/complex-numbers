package org.kotlincook.math

import java.util.*
import kotlin.math.PI

fun main(args: Array<String>) {
    println(3.0 + NaN)
    println(complexOf(1.2, 3.4) == complexOf(1.2, 3.4))
    println(complexOf(-0.0, -0.0).equals(complexOf(0.0, 0.0)))
    println(-0.0 == 0.0)
    println(-0.0 - 0.0)
    println(complexOf(2.0, -2.0))
    println(exp(PI / 2 * I).asString(locale = Locale.US))
    println(cos(ZERO))
    println(sin(PI / 2 * ONE))
    println(2 * ONE / 2.I)
    println(exp(2.I))
    println(sqrt(1))
    println(exp(ln(2.I)))
}