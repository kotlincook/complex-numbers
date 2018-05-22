import kotlin.math.*

fun String.toComplex(): Complex {
    return Complex(0.0, 0.0) // FIXME
}

data class Complex(val re: Double, val im: Double = 0.0) {
    constructor(z: Complex) : this(z.re, z.im)
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

    override fun toString() = asString()

    fun asString(): String = this.re.toString() + (
            when {
                this.im > 0.0 -> "+" + this.im + "i"
                this.im < 0.0 -> "" + this.im + "i"
                else -> ""
            })

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
        x != 0.0 -> atan(y / x)
        y == 0.0 -> 0.0
        y > 0.0 -> PI
        else -> -PI
    }
}

