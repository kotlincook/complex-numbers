package org.kotlincook.math

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
            else -> complexOf(re + x, im )
        }
    }

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
            else -> complexOf(re - x, im )
        }
    }

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

    operator fun div(z: Complex): Complex {
        return when {
            isNaN() || z.isNaN()-> NaN
            isInfinite() -> if (z.isInfinite()) NaN else INF
            z.isInfinite() ->  ZERO
            z.isZero() -> if (isZero()) NaN else INF
            else -> {
                val d = z.re * z.re + z.im * z.im
                complexOf((re * z.re + im * z.im) / d, (im * z.re - re * z.im) / d)
            }
        }
    }
    operator fun div(d: Double): Complex {
       return when {
           isNaN() || d.isNaN()-> NaN
           isInfinite() -> if (d.isInfinite()) NaN else INF
           d.isInfinite() -> ZERO
           d == 0.0 -> if (isZero()) NaN else INF
           else -> complexOf(re / d, im / d)
       }
    }

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

    fun asString(format: String = ""): String {
        val reFormattet = if (format.isEmpty()) re.toString() else String.format(format, re)
        val imFormattet = if (format.isEmpty()) im.toString() else String.format(format, im)

        return reFormattet +
                when {
                    this.im > 0.0 -> "+" + imFormattet + "i"
                    this.im < 0.0 -> "" + imFormattet + "i"
                    else -> ""
                }
    }
}

val Double.I: Complex
    get() = complexOf(0.0, this)

operator fun Double.plus(z: Complex) = z + this

operator fun Double.minus(z: Complex) = -z + this

operator fun Double.times(z: Complex) = z * this

operator fun Double.div(z: Complex) = ONE / z  * this

var toComplex: String.() -> Complex = {
    val parts = StringTokenizer(this, "+-", true)
            .toList().map { it.toString().toLowerCase() }
    when (parts.size) {
        0 -> throw NumberFormatException("empty String")
        1 -> if (parts[0].endsWith("i")) {
            complexOf(0.0, parts[0].removeSuffix("i").toDouble())
        } else {
            complexOf(parts[0].toDouble(), 0.0)
        }
        2 -> if (parts[1].endsWith("i")) {
            complexOf(0.0, (parts[0] + parts[1].removeSuffix("i")).toDouble())
        } else {
            complexOf((parts[0] + parts[1]).toDouble(), 0.0)
        }
        3 -> complexOf(parts[0].toDouble(), (parts[1] + parts[2].removeSuffix("i")).toDouble())
        4 -> complexOf((parts[0] + parts[1]).toDouble(), (parts[2] + parts[3].removeSuffix("i")).toDouble())
        else -> throw NumberFormatException("For input string: \"$this\"")
    }
}

var exp: (Complex) -> Complex = { z ->
    val r: Double = exp(z.re)
    complexOf(r * cos(z.im), r * sin(z.im))
}

var sin: (Complex) -> Complex = { z ->
    complexOf(sin(z.re) * cosh(z.im), cos(z.re) * sinh(z.im))
}

var cos: (Complex) -> Complex = { z ->
    complexOf(cos(z.re) * cosh(z.im), -sin(z.re) * sinh(z.im))
}

var complexOf: (re: Double, im: Double) -> Complex = { re: Double, im: Double -> DefaultComplex(re, im) }
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