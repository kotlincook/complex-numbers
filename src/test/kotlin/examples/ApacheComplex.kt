package org.kotlinmath.examples

import org.kotlinmath.*
import org.kotlinmath.exp
import kotlin.math.PI

/**
 * This class is an example how you could create and use another class (besides DefaultComplex)
 * implementing the interface Complex. In this case a Java implementation of Apache is used
 * as underlying class. Here the exponential funcion and the two operations plus and times are
 * overwritten (resp. replaced) exemplary so that these operation are executed by the Apache
 * library classes.
 */
data class ApacheComplex(val value: org.apache.commons.math3.complex.Complex) : Complex {
    override val re: Double = value.real
    override val im: Double = value.imaginary

    companion object {
        fun activate() {
            complex = { re: Number, im: Number ->
                ApacheComplex(org.apache.commons.math3.complex.Complex(re.toDouble(), im.toDouble()))
            }
            // overwriting exp in order to use the Apache implementation for the exponential function:
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

    /**
     * Here the method <code>plus</code> is overwritten exemplary.
     * @param z multiplicant
     * @return product of this and z
     */
    override operator fun plus(z: Complex) =
            ApacheComplex(value.add((z as ApacheComplex).value))

    override fun toString(): String {
        return zeroSnap().asString("%.2f")
    }
}


fun main() {
    // Using the implementation ApacheComplex instead of DefaultComplex:
    ApacheComplex.activate()
    println(((2.0 + 3.0 * I) * (5.0 + 7.0.I)))
    println(exp(PI * I))
    println(ONE / ZERO)
    val z = 3+4.I
    println(z*z)
}