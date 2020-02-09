package org.kotlincook.math

import java.lang.NumberFormatException
import java.util.*
import kotlin.math.*

interface IComplex {
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
    operator fun not(): IComplex = conj()
    operator fun plus(z: IComplex): IComplex = Complex(re + z.re, im + z.im)
    operator fun minus(z: IComplex): IComplex = Complex(re - z.re, im - z.im)
    operator fun plus(c: Double): IComplex = Complex(re + c, im)
    operator fun minus(c: Double): IComplex = Complex(re - c, im)
    operator fun times(z: IComplex): IComplex = Complex(re * z.re - im * z.im, im * z.re + re * z.im)
    operator fun times(k: Double): IComplex = Complex(re * k, im * k)
    operator fun div(c: IComplex): IComplex {
        val d = c.re * c.re + c.im * c.im
        return Complex((re * c.re + im * c.im) / d, (im * c.re - re * c.im) / d)
    }
    operator fun div(c: Double): IComplex = Complex(re / c, im / c)
    fun conj() = Complex(re, -im)
    fun asString(): String = this.re.toString() + (
            when {
                this.im > 0.0 -> "+" + this.im + "i"
                this.im < 0.0 -> "" + this.im + "i"
                else -> ""
            })
}

val Double.I: IComplex
    get() = Complex(0.0, this)

operator fun Double.plus(c: IComplex): IComplex {
    return Complex(this + c.re, c.im)
}

operator fun Double.minus(c: IComplex): IComplex {
    return Complex(this - c.re, -c.im)
}

operator fun Double.times(c: IComplex): IComplex {
    return Complex(this * c.re, this * c.im)
}

operator fun Double.div(c: IComplex): IComplex {
    val d = c.re * c.re + c.im * c.im
    return Complex((this * c.re) / d, (-this * c.im) / d)
}

fun String.toComplex(): IComplex {
    val parts = StringTokenizer(this, "+-", true)
            .toList().map { it.toString() }
    return when (parts.size) {
        0 -> throw NumberFormatException("empty String")
        1 -> if (parts[0].endsWith("i")) {
            Complex(0.0, parts[0].removeSuffix("i").toDouble())
        } else {
            Complex(parts[0].toDouble(), 0.0)
        }
        2 -> if (parts[1].endsWith("i")) {
            Complex(0.0, (parts[0] + parts[1].removeSuffix("i")).toDouble())
        } else {
            Complex((parts[0] + parts[1]).toDouble(), 0.0)
        }
        3 -> Complex(parts[0].toDouble(), (parts[1] + parts[2].removeSuffix("i")).toDouble())
        4 -> Complex((parts[0] + parts[1]).toDouble(), (parts[2] + parts[3].removeSuffix("i")).toDouble())
        else -> throw NumberFormatException("For input string: \"$this\"")
    }
}

var exp: (IComplex) -> IComplex = { z ->
    val r: Double = exp(z.re)
    Complex(r * cos(z.im), r * sin(z.im))
}

var sin: (IComplex) -> IComplex = { z ->
    Complex(sin(z.re) * cosh(z.im), cos(z.re) * sinh(z.im))
}

var cos: (IComplex) -> IComplex = { z ->
    Complex(cos(z.re) * cosh(z.im), -sin(z.re) * sinh(z.im))
}


var Complex: (re:Double, im: Double) -> IComplex = { re:Double, im:Double -> ComplexImpl(re, im) }
val I = Complex(0.0, 1.0)
val ZERO = Complex(0.0, 0.0)
val ONE = Complex(1.0, 0.0)

data class Polar(val mod: Double, val arg: Double) {
    fun toComplex() = Complex(mod * cos(arg), mod * sin(arg))
}
data class ComplexImpl(override val re: Double, override val im: Double = 0.0): IComplex {
    constructor(z: IComplex) : this(z.re, z.im)
    constructor(polarCoord: Polar) : this(polarCoord.toComplex())
    constructor(str: String) : this(str.toComplex())

    fun toPolar(): Polar = Polar(mod, arg)
    override fun toString(): String = asString()
}



fun main(args: Array<String>) {
    println(exp(PI*I))
}