package org.kotlincook.math

import java.lang.NumberFormatException
import java.util.*
import kotlin.math.*

interface Complex {
    val re: Double
    val im: Double
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
    val mod: Double
        get() {
            return sqrt(re * re + im * im)
        }

    operator fun not(): Complex = conj()
    operator fun plus(z: Complex): Complex = complexOf(re + z.re, im + z.im)
    operator fun minus(z: Complex): Complex = complexOf(re - z.re, im - z.im)
    operator fun plus(c: Double): Complex = complexOf(re + c, im)
    operator fun minus(c: Double): Complex = complexOf(re - c, im)
    operator fun times(z: Complex): Complex = complexOf(re * z.re - im * z.im, im * z.re + re * z.im)
    operator fun times(k: Double): Complex = complexOf(re * k, im * k)
    operator fun div(c: Complex): Complex {
        val d = c.re * c.re + c.im * c.im
        return complexOf((re * c.re + im * c.im) / d, (im * c.re - re * c.im) / d)
    }

    operator fun div(c: Double): Complex = complexOf(re / c, im / c)
    fun conj() = complexOf(re, -im)

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

// Raises a serious error in der IDEA
//var plus: Double.(IComplex) -> IComplex = { c->
//    Complex(this + c.re, c.im)
//}

operator fun Double.plus(c: Complex): Complex {
    return complexOf(this + c.re, c.im)
}

operator fun Double.minus(c: Complex): Complex {
    return complexOf(this - c.re, -c.im)
}

operator fun Double.times(c: Complex): Complex {
    return complexOf(this * c.re, this * c.im)
}

operator fun Double.div(c: Complex): Complex {
    val d = c.re * c.re + c.im * c.im
    return complexOf((this * c.re) / d, (-this * c.im) / d)
}

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

data class Polar(val mod: Double, val arg: Double) {
    fun toComplex() = complexOf(mod * cos(arg), mod * sin(arg))
}

data class DefaultComplex(override val re: Double, override val im: Double = 0.0) : Complex {
    constructor(z: Complex) : this(z.re, z.im)
    constructor(polarCoord: Polar) : this(polarCoord.toComplex())
    constructor(str: String) : this(str.toComplex())

    fun toPolar(): Polar = Polar(mod, arg)
    override fun toString(): String = asString()
}