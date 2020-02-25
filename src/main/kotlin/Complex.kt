package org.kotlincook.math

import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import kotlin.math.*

/**
 * Representation of a Complex number
 */
interface Complex {

    /** Real part */
    val re: Double

    /** Imaginary part */
    val im: Double

    /** The arguemnt of this complex number (angle of the polar coordinate representation) */
    val arg: Double
        get() {
            return when {
                re > 0.0 -> atan(im / re)
                re < 0.0 && im >= 0.0 -> atan(im / re) + PI
                re < 0.0 && im < 0.0 -> atan(im / re) - PI
                re == 0.0 && im > 0.0 -> PI / 2
                re == 0.0 && im < 0.0 -> -PI / 2
                else -> 0.0
            }
        }

    /** The modulus (absolute value) of this complex number (radius of the polar coordinate representation)  */
    val mod: Double
        get() {
            return sqrt(re * re + im * im)
        }

    fun isInfinite() = this === INF
    fun isNaN() = this === NaN
    fun isZero() = this == ZERO

    operator fun plus(z: Complex): Complex {
        return when {
            isNaN() || z.isNaN() -> NaN
            isInfinite() -> if (z.isInfinite()) NaN else INF
            z.isInfinite() -> if (isInfinite()) NaN else INF
            else -> complexOf(re + z.re, im + z.im)
        }
    }

    operator fun plus(x: Double): Complex {
        return when {
            isNaN() || x.isNaN() -> NaN
            isInfinite() -> if (x.isInfinite()) NaN else INF
            x.isInfinite() -> if (isInfinite()) NaN else INF
            else -> complexOf(re + x, im)
        }
    }

    operator fun plus(x: Number) = plus(x.toDouble())

    operator fun minus(z: Complex): Complex {
        return when {
            isNaN() || z.isNaN() -> NaN
            isInfinite() -> if (z.isInfinite()) NaN else INF
            z.isInfinite() -> if (isInfinite()) NaN else INF
            else -> complexOf(re - z.re, im - z.im)
        }
    }

    operator fun minus(x: Double): Complex {
        return when {
            isNaN() || x.isNaN() -> NaN
            isInfinite() -> if (x.isInfinite()) NaN else INF
            x.isInfinite() -> if (isInfinite()) NaN else INF
            else -> complexOf(re - x, im)
        }
    }
    operator fun minus(x: Number) = minus(x.toDouble())

    operator fun times(z: Complex): Complex {
        return when {
            isNaN() || z.isNaN() -> NaN
            isInfinite() -> if (z.isZero()) NaN else INF
            z.isInfinite() -> if (isZero()) NaN else INF
            else -> complexOf(re * z.re - im * z.im, im * z.re + re * z.im)
        }
    }

    operator fun times(d: Double): Complex {
        return when {
            isNaN() || d.isNaN() -> NaN
            isInfinite() -> if (d == 0.0) NaN else INF
            d.isInfinite() -> if (isZero()) NaN else INF
            else -> complexOf(re * d, im * d)
        }
    }
    operator fun times(x: Number) = times(x.toDouble())

    operator fun div(z: Complex): Complex {
        return when {
            isNaN() || z.isNaN() -> NaN
            isInfinite() -> if (z.isInfinite()) NaN else INF
            z.isInfinite() -> ZERO
            z.isZero() -> if (isZero()) NaN else INF
            else -> {
                val d = z.re * z.re + z.im * z.im
                complexOf((re * z.re + im * z.im) / d, (im * z.re - re * z.im) / d)
            }
        }
    }

    operator fun div(d: Double): Complex {
        return when {
            isNaN() || d.isNaN() -> NaN
            isInfinite() -> if (d.isInfinite()) NaN else INF
            d.isInfinite() -> ZERO
            d == 0.0 -> if (isZero()) NaN else INF
            else -> complexOf(re / d, im / d)
        }
    }
    operator fun div(x: Number) = div(x.toDouble())

    operator fun unaryMinus(): Complex {
        return when {
            isNaN() -> NaN
            isInfinite() -> INF
            else -> complexOf(-re, -im)
        }
    }

    operator fun not(): Complex = conj()

    fun conj(): Complex {
        return when {
            isNaN() -> NaN
            isInfinite() -> INF
            else -> complexOf(re, -im)
        }
    }

    fun asString(format: String = "", locale: Locale = Locale.getDefault()): String {
        return when (this) {
            NaN -> "NaN"
            INF -> "Infinity"
            else -> {
                val reFormattet = if (format.isEmpty()) re.toString() else String.format(locale, format, re)
                val imFormattet = when (im) {
                    1.0 -> "i"
                    -1.0 -> "-i"
                    else -> "${if (format.isEmpty()) im.toString() else String.format(locale, format, im)}i"
                }
                if (re == 0.0) {
                    if (im == 0.0) "0.0" else imFormattet
                } else {
                    when {
                        im > 0.0 -> "$reFormattet+$imFormattet"
                        im < 0.0 -> "$reFormattet$imFormattet"
                        else -> reFormattet
                    }
                }
            }
        }
    }
}

val Number.I: Complex
    get() = complexOf(0.0, toDouble())

operator fun Number.plus(z: Complex) = z + this
operator fun Number.minus(z: Complex) = -z + this
operator fun Number.times(z: Complex) = z * this
operator fun Number.div(z: Complex) = ONE / z * this


var toComplex: String.() -> Complex = {

    fun parseIm(arg: String): String {
        val im = arg.removeSuffix("i")
        return if (im.isEmpty()) "1.0" else im
    }

    when (this) {
        "Infinity" -> INF
        "NaN" -> NaN
        else -> {
            val parts = StringTokenizer(this, "+-", true)
                    .toList().map { it.toString().toLowerCase() }
            when (parts.size) {
                0 -> throw NumberFormatException("empty String")
                1 -> if (parts[0].endsWith("i")) {
                    complexOf(0.0, parseIm(parts[0]).toDouble())
                } else {
                    complexOf(parts[0].toDouble(), 0.0)
                }
                2 -> if (parts[1].endsWith("i")) {
                    complexOf(0.0, (parts[0] + parseIm(parts[1])).toDouble())
                } else {
                    complexOf((parts[0] + parts[1]).toDouble(), 0.0)
                }
                3 -> complexOf(parts[0].toDouble(), (parts[1] + parseIm(parts[2])).toDouble())
                4 -> complexOf((parts[0] + parts[1]).toDouble(), (parts[2] + parseIm(parts[3])).toDouble())
                else -> throw NumberFormatException("For input string: \"$this\"")
            }
        }
    }
}

var exp: (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            val r: Double = exp(z.re)
            complexOf(r * cos(z.im), r * sin(z.im))
        }
    }
}
fun exp(z: Number) = Math.exp(z.toDouble())

var sin: (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            val r: Double = exp(z.re)
            complexOf(sin(z.re) * cosh(z.im), cos(z.re) * sinh(z.im))
        }
    }
}
fun sin(z: Number) = Math.sin(z.toDouble())

var cos: (Complex) -> Complex = { z ->
    when (z) {
        NaN, INF -> NaN
        else -> {
            val r: Double = exp(z.re)
            complexOf(cos(z.re) * cosh(z.im), -sin(z.re) * sinh(z.im))
        }
    }
}
fun cos(z: Number) = Math.cos(z.toDouble())

var complexOf: (re: Number, im: Number) -> Complex = { re, im -> DefaultComplex(re.toDouble(), im.toDouble()) }
val I = complexOf(0.0, 1.0)
val ZERO = complexOf(0.0, 0.0)
val ONE = complexOf(1.0, 0.0)
val NaN = complexOf(Double.NaN, Double.NaN)
val INF = complexOf(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)


data class Polar(val mod: Double, val arg: Double) {
    fun toComplex() = complexOf(mod * cos(arg), mod * sin(arg))
}

data class DefaultComplex(override val re: Double, override val im: Double = 0.0) : Complex {
    constructor(z: Complex) : this(z.re, z.im)
    constructor(polarCoord: Polar) : this(polarCoord.toComplex())
    constructor(str: String) : this(str.toComplex())

    fun toPolar(): Polar = Polar(mod, arg)
    // had to be overwritten because of a bug comparing data classes with -0.0 as Double value
    override fun toString(): String = asString()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DefaultComplex
        if (re != other.re) return false
        if (im != other.im) return false
        return true
    }

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}