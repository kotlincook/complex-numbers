package org.kotlincook.math

class ApacheComplex(val value: org.apache.commons.math3.complex.Complex): Complex {
    override val re: Double = value.real
    override val im: Double = value.imaginary

    companion object {
        fun activate() {
            complexOf = { re:Number, im:Number ->
                ApacheComplex(org.apache.commons.math3.complex.Complex(re.toDouble(), im.toDouble())) }
            exp = { z -> ApacheComplex((z as ApacheComplex).value.exp()) }
        }
    }

    override operator fun times(z: Complex) =
            ApacheComplex(value.multiply((z as ApacheComplex).value))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ApacheComplex
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