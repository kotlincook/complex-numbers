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

    @Test
    fun testPow() {
        assertEquals(ONE, pow(0, ZERO))
        assertEquals(ZERO, pow(0, 3.R))
        assertQuasiEquals(ONE, pow(E, 2 * PI.I))
        assertQuasiEquals(-2.R, pow(E, ln(2) + PI.I))
        assertEquals(ONE, pow(ZERO, ZERO))
        assertEquals(ONE, pow(ONE, 3.R))
        assertQuasiEquals(0.20787957635076193.R, pow(I, I))
        assertQuasiEquals(0.12900959407446697 + 0.03392409290517014.I, pow((1 + 2.I), (3 + 4.I)))
    }

    @Test
    fun testPowZetaValues() {
        val w = 0.5 + 14.134725141734693790457251983562.I
        assertQuasiEquals(ONE, pow(1, w))
        assertQuasiEquals(-1.3171414230751064 - 0.5149159850108396.I, pow(2, w))
        assertQuasiEquals(-1.7042590259602113 + 0.30903263975372064.I, pow(3, w))
        assertQuasiEquals(1.4697230567606336 + 1.356434346522595.I, pow(4, w))
        assertQuasiEquals(-1.6241462732233494 - 1.536928392338012.I, pow(5, w))
        assertQuasiEquals(-6.366647462900551 + 7.711407140278745.I, pow(100, w))
    }

}