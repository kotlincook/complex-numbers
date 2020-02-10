package org.kotlincook.math

import kotlin.math.PI

fun main(args: Array<String>) {
    ApacheComplex.activate()
    println((2.0 + 3.0*I) * (5.0 + 7.0.I))
    println(exp(PI *I))
}