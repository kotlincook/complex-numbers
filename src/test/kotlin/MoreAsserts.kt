package org.kotlinmath
import kotlin.math.abs
import kotlin.test.assertTrue

fun assertQuasiEquals(expected: Number, actual: Number,
                              precision: Double = Complex.DEFAULT_ZERO_SNAP_PRECISION,
                              message: String? = null) =
        assertTrue(abs(expected.toDouble() - actual.toDouble()) < precision,
                message ?: "Expected <$expected>, actual <$actual>")

fun assertQuasiEquals(expected: Complex, actual: Complex,
                              precision: Double = Complex.DEFAULT_ZERO_SNAP_PRECISION,
                              message: String? = null) =
        assertTrue((expected - actual).mod < precision,
                message ?: "Expected <$expected>, actual <$actual>")