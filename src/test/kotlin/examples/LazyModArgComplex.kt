package org.kotlinmath.examples

import org.kotlinmath.DefaultComplex
import org.kotlinmath.complex

class LazyModArgComplex(override val re: Double, override val im: Double = 0.0): DefaultComplex(re, im) {

    companion object {
        fun activate() {
            complex = { re: Number, im: Number ->
                LazyModArgComplex(re.toDouble(), im.toDouble())
            }
        }
    }

    override val mod : Double by lazy {
        super.mod
    }

    override val arg : Double by lazy {
        super.arg
    }

}