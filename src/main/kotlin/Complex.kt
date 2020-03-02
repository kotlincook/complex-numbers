package org.kotlinmath

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
            return kotlin.math.sqrt(re * re + im * im)
        }

    /**
     *  checks infinity property (remark that in case of complex numbers there is only one unsigned infinity)
     *  @return true if this is infinite
     */
    fun isInfinite() = this === INF

    /**
     * checks the "not a number" property (NaN represents an essential singularity)
     * @return true if this is NaN
     */
    fun isNaN() = this === NaN

    /**
     * checks to zero
     *  @return true if this is zero
     */
    fun isZero() = this == ZERO

    /**
     * Plus operator adding two complex numbers
     * @param z the summand
     * @return sum of this and z
     */
    operator fun plus(z: Complex): Complex {
        return when {
            isNaN() || z.isNaN() -> NaN
            isInfinite() -> if (z.isInfinite()) NaN else INF
            z.isInfinite() -> if (isInfinite()) NaN else INF
            else -> complexOf(re + z.re, im + z.im)
        }
    }

    /**
     * Plus operator adding a complex number and a number of type Double
     * @param x the summand
     * @return sum of this and x
     */
    operator fun plus(x: Double): Complex {
        return when {
            isNaN() || x.isNaN() -> NaN
            isInfinite() -> if (x.isInfinite()) NaN else INF
            x.isInfinite() -> if (isInfinite()) NaN else INF
            else -> complexOf(re + x, im)
        }
    }

    /**
     * Plus operator adding a complex number and one of type Number except Double
     * @param x the summand
     * @return sum of this and x
     */
    operator fun plus(x: Number) = plus(x.toDouble())

    /**
     * Minus operator subtracting two complex numbers
     * @param z the minuend
     * @return difference of this and x
     */
    operator fun minus(z: Complex): Complex {
        return when {
            isNaN() || z.isNaN() -> NaN
            isInfinite() -> if (z.isInfinite()) NaN else INF
            z.isInfinite() -> if (isInfinite()) NaN else INF
            else -> complexOf(re - z.re, im - z.im)
        }
    }

    /**
     * Minus operator subtracting a complex number and one of type Double
     * @param x the minuend
     * @return difference of this and x
     */
    operator fun minus(x: Double): Complex {
        return when {
            isNaN() || x.isNaN() -> NaN
            isInfinite() -> if (x.isInfinite()) NaN else INF
            x.isInfinite() -> if (isInfinite()) NaN else INF
            else -> complexOf(re - x, im)
        }
    }

    /**
     * Minus operator subtracting a complex number and one of type Number except Double
     * @param x the minuend
     * @return difference of this and x
     */
    operator fun minus(x: Number) = minus(x.toDouble())

    /**
     * Times operator multiplying two complex numbers
     * @param z the multiplicant
     * @return product of this and z
     */
    operator fun times(z: Complex): Complex {
        return when {
            isNaN() || z.isNaN() -> NaN
            isInfinite() -> if (z.isZero()) NaN else INF
            z.isInfinite() -> if (isZero()) NaN else INF
            else -> complexOf(re * z.re - im * z.im, im * z.re + re * z.im)
        }
    }

    /**
     * Times operator multiplying a complex number and one of type Double
     * @param x the multiplicant
     * @return product of this and x
     */
    operator fun times(x: Double): Complex {
        return when {
            isNaN() || x.isNaN() -> NaN
            isInfinite() -> if (x == 0.0) NaN else INF
            x.isInfinite() -> if (isZero()) NaN else INF
            else -> complexOf(re * x, im * x)
        }
    }

    /**
     * Times operator multiplying a complex number and one of type Number except Double
     * @param x the multiplicant
     * @return the product of this and x
     */
    operator fun times(x: Number) = times(x.toDouble())

    /**
     * Divide operator dividing two complex numbers
     * @param z the denominator
     * @return product of this and z
     */
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

    /**
     * Divide operator dividing a complex number and one of type Double
     * @param z the divisor
     * @return division of this and z
     */
    operator fun div(x: Double): Complex {
        return when {
            isNaN() || x.isNaN() -> NaN
            isInfinite() -> if (x.isInfinite()) NaN else INF
            x.isInfinite() -> ZERO
            x == 0.0 -> if (isZero()) NaN else INF
            else -> complexOf(re / x, im / x)
        }
    }

    /**
     * Divide operator dividing a complex number and one of type Number except Double
     * @param x the divisor
     * @return division of this and z
     */
    operator fun div(x: Number) = div(x.toDouble())

    /**
     * Negates a complex number
     * @return negation of this
     */
    operator fun unaryMinus(): Complex {
        return when {
            isNaN() -> NaN
            isInfinite() -> INF
            else -> complexOf(-re, -im)
        }
    }

    /**
     * Calculates the complex conjugation
     * @return complex conjugation of this
     */
    operator fun not(): Complex = conj()

    /**
     * Calculates the complex conjugation
     * @return complex conjugation of this
     */
    fun conj(): Complex {
        return when {
            isNaN() -> NaN
            isInfinite() -> INF
            else -> complexOf(re, -im)
        }
    }

    /**
     * A string representation of a complex number (this) in the Form "2.5+3.1i" for example.
     * @param format This parameter affects the real an the imaginary part equally.
     * @param locale The locale determines e.g. whether a dot or a comma is output.
     */
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

/**
 * Makes a number "imaginary". The result is the same as if the number (this) is multiplied by I.
 * @return this * I
 */
val Number.I: Complex
    get() = complexOf(0, toDouble())

/**
 * Creates a complex number with this as real part and no imaginary part
 * @return this as complex number
 */
val Number.R: Complex
    get() = complexOf(toDouble(), 0)

/**
 * Plus operator adding a number of type Number and a complex one
 * @param z the summand
 * @return sum of this and z
 */
operator fun Number.plus(z: Complex) = z + this

/**
 * Minus operator sutracting a number of type Number and a complex one
 * @param z the minuend
 * @return diffenence of this and z
 */
operator fun Number.minus(z: Complex) = -z + this

/**
 * Times operator multiplying a number of type Number and a complex one
 * @param z the multiplicant
 * @return product of this and z
 */
operator fun Number.times(z: Complex) = z * this

/**
 * Division operator dividing a number of type Number and a complex one
 * @param z the divisor
 * @return division of this and z
 */
operator fun Number.div(z: Complex) = ONE / z * this

/**
 * Creates a complex number from a string. A valid representation is e.g. "2.5+3.1i"
 * @return the created complex number
 */
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
                    .toList().map { it.toString().replace('I', 'i') }
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

/**
 * Creates a complex number from real and imaginary part.
 * Here instances of class <code>DefaultComplex</code> are created which implements 
 * the interface <code>Complex</code> is used by
 * the factory function <code>toComplex</code>. If you would like to use your own
 * implementation of <code>Complex</code> you can do this by replacing <code>toComplex</code>
 * with a factory which is creating your custom class. So, the entire application code can
 * remain the same.
 * @param re the real part
 * @param im the imaginary part
 * @return the created complex number
 */
var complexOf: (re: Number, im: Number) -> Complex = { re, im -> DefaultComplex(re.toDouble(), im.toDouble()) }

/** The imaginary unit i as constant */
val I = complexOf(0, 1)

/** Number 0 as complex constant */
val ZERO = complexOf(0, 0)

/** The real unit 1 as constant */
val ONE = complexOf(1, 0)

/** "Not a number" represents a essential singularity */
val NaN = complexOf(Double.NaN, Double.NaN)

/** Infinity represents the north pole of the complex sphere. */
val INF = complexOf(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)


/**
 * A default implementation of the interface <code>Complex</code>. This class is used by
 * the factory function <code>toComplex</code>. If you would like to use your own
 * implementation of <code>Complex</code> you can do this by replacing <code>toComplex</code>
 * with a factory which is creating your custom class. So, the entire application code can
 * remain the same.
 */
data class DefaultComplex(override val re: Double, override val im: Double = 0.0) : Complex {
    constructor(z: Complex) : this(z.re, z.im)
    constructor(str: String) : this(str.toComplex())

    // the following three standard functions had to be overwritten because of a bug comparing
    // data classes with -0.0 as Double value. Without these overwrites would be
    // DefaultComplex(0.0, 0.0) != DefaultComplex(-0.0, 0.0) although 0.0 == -0.0:

    open override fun toString(): String = asString()

    open override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other === null) return false
        if (other is Complex) {
            if (re != other.re) return false
            if (im != other.im) return false
            return true
        }
        if (other is Number) {
            return im == 0.0 && re == other.toDouble()
        }
        return false
    }

    open override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}