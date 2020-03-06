package org.kotlinmath.examples

import org.kotlinmath.DefaultComplex
import org.kotlinmath.complex

open class CachingModArgComplex(override val re: Double, override val im: Double = 0.0) : DefaultComplex(re, im) {

    companion object {
        fun activate() {
            complex = { re: Number, im: Number ->
                CachingModArgComplex(re.toDouble(), im.toDouble())
            }
        }
    }

    private var modCached: Double? = null

    override val mod: Double
        get() = synchronized(this) {
            if (modCached == null) modCached = super.mod
            modCached!!
        }

    private var argCached: Double? = null

    override val arg: Double
        get() = synchronized(this) {
            if (argCached == null) argCached = super.arg
            argCached!!
        }
}