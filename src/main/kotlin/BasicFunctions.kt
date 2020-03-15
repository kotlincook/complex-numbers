package org.kotlinmath

import kotlin.math.*

/**
 * Exponential function
 */
var exp : (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            val r: Double = kotlin.math.exp(z.re)
            complex(r * kotlin.math.cos(z.im), r * kotlin.math.sin(z.im))
        }
    }
}

/**
 * Exponential function
 * @param z input
 * @return exp(z)
 */
fun exp(z: Number) = exp(complex(z.toDouble(), 0))

/**
 * Main branch of the Logarithmic function
 */
var ln :(Complex) -> Complex = { z ->
    when (z) {
        ZERO, INF, NaN -> NaN
        else -> complex(kotlin.math.ln(z.mod), atan2(z.im, z.re))
    }
}

/**
 * Logarithmic function
 * @param z input
 * @return ln(z)
 */
fun ln(z: Number) = ln(complex(z.toDouble(), 0))

/**
 * Sinus function
 */
var sin : (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            complex(
                    kotlin.math.sin(z.re) * kotlin.math.cosh(z.im),
                    kotlin.math.cos(z.re) * kotlin.math.sinh(z.im))
        }
    }
}

/**
 * Sinus function
 * @param z input
 * @return sin(z)
 */
fun sin(z: Number) = sin(complex(z.toDouble(), 0))

/**
 * Cosinus function
 */
var cos : (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            complex(
                    kotlin.math.cos(z.re) * kotlin.math.cosh(z.im),
                    -kotlin.math.sin(z.re) * kotlin.math.sinh(z.im))
        }
    }
}

/**
 * Cosinus function
 * @param z input
 * @return cos(z)
 */
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
            val t: Double = kotlin.math.sqrt((abs(z.re) + z.mod) / 2)
            if (z.re >= 0) {
                complex(t, z.im / (2 * t))
            } else {
                complex(kotlin.math.abs(z.im) / (2 * t), Math.copySign(1.0, z.im) * t)
            }
        }
    }
}

/**
 * Square Root function
 * @param z input
 * @return sqrt(z)
 */
fun sqrt(z: Number) = sqrt(complex(z.toDouble(), 0))

/**
 * Calculates the complex power. Please note, that similar to ln and sqrt the default
 * value is returned here.
 * @param z basis
 * @param w exponent
 * @return the power z^w
 */
var pow : (Complex, Complex) -> Complex = { z, w ->
    pow(z.mod, w) * exp(z.arg.I * w)
}

/**
 * The power function
 * @param x base
 * @param w exponent
 * @return x^w
 */
fun pow(x: Number, w:Complex): Complex {
    val d = x.toDouble()
    return when {
        d < 0.0 -> NaN
        d.isInfinite() -> NaN
        d.isNaN() -> NaN
        w.isInfinite() -> NaN
        w.isNaN() -> NaN
        w.isZero() -> ONE
        d == 0.0 -> ZERO
        else -> exp(kotlin.math.ln(d) * w)
    }
}