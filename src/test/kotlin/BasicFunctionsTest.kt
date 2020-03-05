package org.kotlinmath

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.lang.Math.PI
import kotlin.math.E
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BasicFunctionsTest {

    private fun assertEquals(expected: Number, actual: Number, precision: Double, message: String? = null) =
            assertTrue(Math.abs(expected.toDouble() - actual.toDouble()) < precision,
                    message ?: "Expected <$expected>, actual <$actual>")

    private fun assertEquals(expected: Complex, actual: Complex, precision: Double, message: String? = null) =
            assertTrue((expected - actual).mod < precision,
                    message ?: "Expected <$expected>, actual <$actual>")

    @Test
    fun testExp() {
        assertEquals(ONE, exp(0))
        assertEquals(E.R, exp(1))
        assertEquals(-ONE, exp(PI.I), EPS)
        assertEquals(I, exp(PI.I / 2), EPS)
        assertEquals((E * E).I, exp(2 + PI.I / 2), EPS)
        assertEquals(-I, exp(3 * PI.I / 2), EPS)
        assertEquals(NaN, exp(INF))
        assertEquals(NaN, exp(NaN))
    }

    @Test
    fun testSin() {
        assertEquals(ZERO, sin(0))
        assertEquals(ONE, sin(PI / 2))
        assertEquals(1.1752011936438014.I, sin(I), EPS)
        assertEquals(-1.1752011936438014.I, sin(-I), EPS)
        assertEquals(NaN, sin(INF))
        assertEquals(NaN, sin(NaN))
    }

    @Test
    fun testCos() {
        assertEquals(ONE, cos(0))
        assertEquals(ZERO, cos(PI / 2), EPS)
        assertEquals(-ONE, cos(PI))
        assertEquals(1.543080634815244.R, cos(I), EPS)
        assertEquals(1.543080634815244.R, cos(-I), EPS)
        assertEquals(NaN, cos(INF))
        assertEquals(NaN, cos(NaN))
    }

    @Test
    fun testLn() {
        assertEquals(NaN, ln(0))
        assertEquals(ZERO, ln(1))
        assertEquals(ONE, ln(E))
        assertEquals(2.R, ln(E * E))
        assertEquals(PI.I, ln(-1), EPS)
        assertEquals(PI.I / 2, ln(I), EPS)
        assertEquals(-PI.I / 2, ln(-I), EPS)
        assertEquals(NaN, ln(0))
        assertEquals(NaN, ln(INF))
        assertEquals(NaN, ln(NaN))
    }

    @Test
    fun testSqrt() {
        assertEquals(ZERO, sqrt(0))
        assertEquals(ONE, sqrt(1))
        assertEquals(2.R, sqrt(4))
        assertEquals(2.I, sqrt(-4))
        assertEquals(3 + 4.I, sqrt(-7 + 24.I))
        assertEquals(sqrt(0.5) + sqrt(0.5) * I, sqrt(I), EPS)
        assertEquals(sqrt(0.5) - sqrt(0.5) * I, sqrt(-I), EPS)
        assertEquals(INF, sqrt(INF))
        assertEquals(NaN, sqrt(NaN))
    }

}