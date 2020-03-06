package org.kotlinmath

import org.junit.jupiter.api.Test
import java.lang.Math.PI
import kotlin.math.E
import kotlin.test.assertEquals

class BasicFunctionsTest {

    @Test
    fun testExp() {
        assertEquals(ONE, exp(0))
        assertEquals(E.R, exp(1))
        assertQuasiEquals(-ONE, exp(PI.I))
        assertQuasiEquals(I, exp(PI.I / 2))
        assertQuasiEquals((E * E).I, exp(2 + PI.I / 2))
        assertQuasiEquals(-I, exp(3 * PI.I / 2))
        assertEquals(NaN, exp(INF))
        assertEquals(NaN, exp(NaN))
    }

    @Test
    fun testSin() {
        assertEquals(ZERO, sin(0))
        assertEquals(ONE, sin(PI / 2))
        assertQuasiEquals(1.1752011936438014.I, sin(I))
        assertQuasiEquals(-1.1752011936438014.I, sin(-I))
        assertEquals(NaN, sin(INF))
        assertEquals(NaN, sin(NaN))
    }

    @Test
    fun testCos() {
        assertEquals(ONE, cos(0))
        assertQuasiEquals(ZERO, cos(PI / 2))
        assertEquals(-ONE, cos(PI))
        assertQuasiEquals(1.543080634815244.R, cos(I))
        assertQuasiEquals(1.543080634815244.R, cos(-I))
        assertEquals(NaN, cos(INF))
        assertEquals(NaN, cos(NaN))
    }

    @Test
    fun testLn() {
        assertEquals(NaN, ln(0))
        assertEquals(ZERO, ln(1))
        assertEquals(ONE, ln(E))
        assertEquals(2.R, ln(E * E))
        assertQuasiEquals(PI.I, ln(-1))
        assertQuasiEquals(PI.I / 2, ln(I))
        assertQuasiEquals(-PI.I / 2, ln(-I))
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
        assertQuasiEquals(sqrt(0.5) + sqrt(0.5) * I, sqrt(I))
        assertQuasiEquals(sqrt(0.5) - sqrt(0.5) * I, sqrt(-I))
        assertEquals(INF, sqrt(INF))
        assertEquals(NaN, sqrt(NaN))
    }

}