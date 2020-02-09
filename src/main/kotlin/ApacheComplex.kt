package org.kotlincook.math

import org.apache.commons.math3.complex.Complex
import kotlin.math.PI

class ApacheComplexImpl(z: Complex): IComplex {
    override val re: Double = z.real
    override val im: Double = z.imaginary

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ApacheComplexImpl
        if (re != other.re) return false
        if (im != other.im) return false
        return true
    }

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
    override fun toString(): String = asString()
}


fun main(args: Array<String>) {
    Complex = { re:Double, im:Double -> ApacheComplexImpl(Complex(re, im)) }
    println((2.0 + 3.0.I) * (5.0 + 7.0.I))
    println(exp(PI *I))
}