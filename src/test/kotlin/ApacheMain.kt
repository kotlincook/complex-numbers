package org.kotlincook.math

import java.util.*
import kotlin.math.PI

fun main(args: Array<String>) {
    println(3.0 + NaN)
    println(complexOf(1.2, 3.4) == complexOf(1.2, 03.4))
    println(complexOf(-0.0, -0.0).equals(complexOf(0.0, 0.0)))
    println(-0.0 == 0.0)
    println(-0.0 - 0.0)
    println(complexOf(2.0, -2.0))
    println(exp(PI / 2 * I).asString(locale = Locale.US))
    println(cos(ZERO))
    println(sin(PI / 2 * ONE))
    println(2*ONE/2.I)

//    ApacheComplex.activate()
//    println(((2.0 + 3.0 * I) * (5.0 + 7.0.I)).asString("%.2f"))
//    println(exp(PI * I).asString("%.2f"))
//    println(1.0 - (1.0/0.0))
//    println(ONE/ZERO)
//
//    val z = 3.0+ 4.0.I
//
//    try {
//        val inf = 0.0 / 0.0
//        println(inf / Double.POSITIVE_INFINITY + 12)
//    } catch (e: Exception) {
//        println(e)
//    }
}