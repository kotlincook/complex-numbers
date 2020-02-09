package org.kotlincook.math

import java.lang.NumberFormatException
import java.util.*
import kotlin.math.*

val Double.i: Complex
    get() = Complex(0.0, this)

data class Polar(val mod: Double, val arg: Double) {
    fun toComplex() = Complex(mod * cos(arg), mod * sin(arg))
}

const val π = PI
val i = Complex(0.0, 1.0)
val zero = Complex(0.0, 0.0)
val one = Complex(1.0, 0.0)

data class Complex(val re: Double, val im: Double = 0.0) {
    constructor(z: Complex) : this(z.re, z.im)
    constructor(polarCoord: Polar) : this(polarCoord.toComplex())
    constructor(str: String) : this(str.toComplex())

    val arg: Double
        get() {
            return arg(this)
        }
    val mod: Double
        get() {
            return mod(this)
        }

    operator fun not(): Complex = conj(this)
    operator fun plus(z: Complex): Complex = Complex(re + z.re, im + z.im)
    operator fun minus(z: Complex): Complex = Complex(re - z.re, im - z.im)
    operator fun plus(c: Double): Complex = Complex(re + c, im)
    operator fun minus(c: Double): Complex = Complex(re - c, im)
    operator fun times(z: Complex): Complex = Complex(re * z.re - im * z.im, im * z.re + re * z.im)
    operator fun times(k: Double): Complex = Complex(re * k, im * k)
    operator fun div(c: Complex): Complex {
        val d = c.re * c.re + c.im * c.im
        return Complex((re * c.re + im * c.im) / d, (im * c.re - re * c.im) / d)
    }

    operator fun div(c: Double): Complex = Complex(re / c, im / c)

    fun toPolar(): Polar = Polar(mod(this), arg(this))

    override fun toString(): String = this.re.toString() + (
            when {
                this.im > 0.0 -> "+" + this.im + "i"
                this.im < 0.0 -> "" + this.im + "i"
                else -> ""
            })
}

fun String.toComplex(): Complex {
    val parts = StringTokenizer(this, "+-", true)
            .toList().map { it.toString() }
    return when (parts.size) {
        0 -> throw NumberFormatException("empty String")
        1 -> if (parts[0].endsWith("i")) {
                Complex(0.0, parts[0].removeSuffix("i").toDouble())
             } else {
                Complex(parts[0].toDouble())
             }
        2 -> if (parts[1].endsWith("i")) {
                Complex(0.0, (parts[0] + parts[1].removeSuffix("i")).toDouble())
             } else {
                Complex((parts[0] + parts[1]).toDouble())
             }
        3 -> Complex(parts[0].toDouble(), (parts[1] + parts[2].removeSuffix("i")).toDouble())
        4 -> Complex((parts[0] + parts[1]).toDouble(), (parts[2] + parts[3].removeSuffix("i")).toDouble())
        else -> throw NumberFormatException("For input string: \"$this\"")
    }
}

fun exp(z: Complex): Complex {
    val r: Double = exp(z.re)
    return Complex(r * cos(z.im), r * sin(z.im))
}

fun sin(z: Complex): Complex {
    val (x, y) = z
    return Complex(sin(x) * cosh(y), cos(x) * sinh(y))
}

fun cos(z: Complex): Complex {
    val (x, y) = z
    return Complex(cos(x) * cosh(y), -sin(x) * sinh(y))
}

fun conj(z: Complex) = Complex(z.re, -z.im)
fun mod(z: Complex) = sqrt(z.re * z.re + z.im * z.im)
fun arg(z: Complex): Double {
    val (x, y) = z
    return when {
        x > 0.0 -> atan(y / x)
        x < 0.0 && y >= 0.0 -> atan(y / x) + PI
        x < 0.0 && y < 0.0 -> atan(y / x) - PI
        x == 0.0 && y > 0.0 -> PI / 2
        x == 0.0 && y < 0.0 -> -PI / 2
        else -> 0.0
    }
}

operator fun Double.plus(c: Complex): Complex {
    return Complex(this + c.re, c.im)
}

operator fun Double.minus(c: Complex): Complex {
    return Complex(this - c.re, -c.im)
}

operator fun Double.times(c: Complex): Complex {
    return Complex(this * c.re, this * c.im)
}

operator fun Double.div(c: Complex): Complex {
    val d = c.re * c.re + c.im * c.im
    return Complex((this * c.re) / d, (-this * c.im) / d)
}