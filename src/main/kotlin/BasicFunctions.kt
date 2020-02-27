package org.kotlincook.math

import kotlin.math.*
import kotlin.system.measureTimeMillis

/**
 * Exponential function
 */
var exp : (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            val r: Double = Math.exp(z.re)
            complexOf(r * kotlin.math.cos(z.im), r * kotlin.math.sin(z.im))
        }
    }
}

fun exp(z: Number) = exp(complexOf(z.toDouble(), 0))

/**
 * Main branch of the Logarithmic function
 */
var ln :(Complex) -> Complex = { z ->
    when (z) {
        ZERO, NaN -> NaN
        INF -> INF
        else -> complexOf(kotlin.math.ln(z.mod), atan2(z.im, z.re))
    }
}

fun ln(z: Number) = ln(complexOf(z.toDouble(), 0))

/**
 * Sinus function
 */
var sin : (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            val r: Double = Math.exp(z.re)
            complexOf(
                    kotlin.math.sin(z.re) * kotlin.math.cosh(z.im),
                    kotlin.math.cos(z.re) * kotlin.math.sinh(z.im))
        }
    }
}

fun sin(z: Number) = sin(complexOf(z.toDouble(), 0))

/**
 * Cosinus function
 */
var cos : (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            val r: Double = Math.exp(z.re)
            complexOf(
                    kotlin.math.cos(z.re) * kotlin.math.cosh(z.im),
                    -kotlin.math.sin(z.re) * kotlin.math.sinh(z.im))
        }
    }
}

fun cos(z: Number) = cos(complexOf(z.toDouble(), 0))

/**
 * Main branch of the Square Root function
 */
var sqrt : (Complex) -> Complex = { z ->
    when(z) {
        ZERO -> ZERO
        INF -> INF
        NaN -> NaN
        else -> {
            val t: Double = Math.sqrt((Math.abs(z.re) + z.mod) / 2)
            if (z.re >= 0) {
                complexOf(t, z.im / (2 * t))
            } else {
                complexOf(kotlin.math.abs(z.im) / (2 * t), Math.copySign(1.0, z.im) * t)
            }
        }
    }
}

fun sqrt(z: Number) = sqrt(complexOf(z.toDouble(), 0))