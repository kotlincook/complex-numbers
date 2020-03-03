package org.kotlinmath

import kotlin.math.*

/**
 * Exponential function
 */
var exp : (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            val r: Double = Math.exp(z.re)
            complex(r * kotlin.math.cos(z.im), r * kotlin.math.sin(z.im))
        }
    }
}

fun exp(z: Number) = exp(complex(z.toDouble(), 0))

/**
 * Main branch of the Logarithmic function
 */
var ln :(Complex) -> Complex = { z ->
    when (z) {
        ZERO, NaN -> NaN
        INF -> INF
        else -> complex(kotlin.math.ln(z.mod), atan2(z.im, z.re))
    }
}

fun ln(z: Number) = ln(complex(z.toDouble(), 0))

/**
 * Sinus function
 */
var sin : (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            val r: Double = Math.exp(z.re)
            complex(
                    kotlin.math.sin(z.re) * kotlin.math.cosh(z.im),
                    kotlin.math.cos(z.re) * kotlin.math.sinh(z.im))
        }
    }
}

fun sin(z: Number) = sin(complex(z.toDouble(), 0))

/**
 * Cosinus function
 */
var cos : (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            val r: Double = Math.exp(z.re)
            complex(
                    kotlin.math.cos(z.re) * kotlin.math.cosh(z.im),
                    -kotlin.math.sin(z.re) * kotlin.math.sinh(z.im))
        }
    }
}

fun cos(z: Number) = cos(complex(z.toDouble(), 0))

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
                complex(t, z.im / (2 * t))
            } else {
                complex(kotlin.math.abs(z.im) / (2 * t), Math.copySign(1.0, z.im) * t)
            }
        }
    }
}

fun sqrt(z: Number) = sqrt(complex(z.toDouble(), 0))