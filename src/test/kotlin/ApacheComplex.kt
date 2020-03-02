package org.kotlinmath

import kotlin.math.PI

data class ApacheComplex(val value: org.apache.commons.math3.complex.Complex) : Complex {
    override val re: Double = value.real
    override val im: Double = value.imaginary

    companion object {
        fun activate() {
            complexOf = { re: Number, im: Number ->
                ApacheComplex(org.apache.commons.math3.complex.Complex(re.toDouble(), im.toDouble()))
            }
            // overwrite exp in order to usse the implementation of Apache for the exponential function:
            exp = { z -> ApacheComplex((z as ApacheComplex).value.exp()) }
        }
    }

    /**
     * Here the method <code>times</code> is overwritten exemplary.
     * @param z multiplicant
     * @return product of this and z
     */
    override operator fun times(z: Complex) =
            ApacheComplex(value.multiply((z as ApacheComplex).value))
}


fun main(args: Array<String>) {
    // Using the implementation ApacheComplex instead of DefaultComplex:
    ApacheComplex.activate()
    println(((2.0 + 3.0 * I) * (5.0 + 7.0.I)).asString("%.2f"))
    println(exp(PI * I).asString("%.2f"))
    println(1.0 - (1.0 / 0.0))
    println(ONE / ZERO)
    val z = 3.0 + 4.0.I
}