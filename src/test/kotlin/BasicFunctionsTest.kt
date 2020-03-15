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
    fun testPowDoubleComplex() {
        val w0 = ln(2) + PI.I
        val w1 = 2 * PI.I
        val w2 = 2.R
        val w3 = 0.5 + 14.13472514173469379.I

        assertEquals(ONE, pow(0, ZERO))
        assertEquals(ZERO, pow(0, ONE))
        assertEquals(ZERO, pow(0, I))
        assertEquals(ZERO, pow(0, w0))
        assertEquals(ZERO, pow(0, w1))
        assertEquals(ZERO, pow(0, w2))
        assertEquals(NaN, pow(0, INF))
        assertEquals(NaN, pow(0, NaN))

        assertEquals(ONE, pow(1, ZERO))
        assertEquals(ONE, pow(1, ONE))
        assertEquals(ONE, pow(1, I))
        assertEquals(ONE, pow(1, w0))
        assertEquals(ONE, pow(1, w1))
        assertEquals(ONE, pow(1, w2))
        assertEquals(NaN, pow(1, INF))
        assertEquals(NaN, pow(1, NaN))

        assertEquals(ONE, pow(E, ZERO))
        assertQuasiEquals(E.R, pow(E, ONE))
        assertQuasiEquals(0.54030230586813 + 0.84147098480790.I, pow(E, I))
        assertQuasiEquals(-2.R, pow(E, w0))
        assertQuasiEquals(ONE, pow(E, w1))
        assertQuasiEquals(7.38905609893065.R, pow(E, w2))
        assertEquals(NaN, pow(E, INF))
        assertEquals(NaN, pow(E, NaN))

        assertQuasiEquals(-1.3171414230751064 - 0.5149159850108396.I, pow(2, w3))
        assertQuasiEquals(-1.7042590259602113 + 0.30903263975372064.I, pow(3, w3))
        assertQuasiEquals(1.4697230567606336 + 1.356434346522595.I, pow(4, w3))
        assertQuasiEquals(-1.6241462732233494 - 1.536928392338012.I, pow(5, w3))
        assertQuasiEquals(-6.366647462900551 + 7.711407140278745.I, pow(100, w3))

        for (w in listOf(ZERO, ONE, I, 3.R, w1, w0, INF, NaN)) {
            assertEquals(NaN, pow(-1, w))
            assertEquals(NaN, pow(Double.POSITIVE_INFINITY, w))
            assertEquals(NaN, pow(Double.NEGATIVE_INFINITY, w))
            assertEquals(NaN, pow(Double.NaN, w))
        }
    }


    @Test
    fun testPowComplexComplex() {
        val w0 = ln(2) + PI.I
        val w1 = 2 * PI.I
        val w2 = 2.R

        assertEquals(ONE, pow(ZERO, ZERO))
        assertEquals(ZERO, pow(ZERO, ONE))
        assertEquals(ZERO, pow(ZERO, I))
        assertEquals(ZERO, pow(ZERO, w0))
        assertEquals(ZERO, pow(ZERO, w1))
        assertEquals(ZERO, pow(ZERO, w2))
        assertEquals(NaN, pow(ZERO, INF))
        assertEquals(NaN, pow(ZERO, NaN))

        assertEquals(ONE, pow(ONE, ZERO))
        assertEquals(ONE, pow(ONE, ONE))
        assertEquals(ONE, pow(ONE, I))
        assertEquals(ONE, pow(ONE, w0))
        assertEquals(ONE, pow(ONE, w1))
        assertEquals(ONE, pow(ONE, w2))
        assertEquals(NaN, pow(ONE, INF))
        assertEquals(NaN, pow(ONE, NaN))

        assertEquals(ONE, pow(E.R, ZERO))
        assertQuasiEquals(E.R, pow(E.R, ONE))
        assertQuasiEquals(0.54030230586813 + 0.84147098480790.I, pow(E.R, I))
        assertQuasiEquals(-2.R, pow(E.R, w0))
        assertQuasiEquals(ONE, pow(E.R, w1))
        assertQuasiEquals(7.38905609893065.R, pow(E.R, w2))
        assertEquals(NaN, pow(E.R, INF))
        assertEquals(NaN, pow(E.R, NaN))

        assertQuasiEquals(0.20787957635076193.R, pow(I, I))
        assertQuasiEquals(0.12900959407446697 + 0.03392409290517014.I, pow((1 + 2.I), (3 + 4.I)))
        assertQuasiEquals(-0.003293803714486435 - 0.031809901650039635.I, pow(w0, w0))
        assertQuasiEquals(9.918799222181348E-5 + 1.764518526767741E-4.I, pow(w0, w1))
        assertQuasiEquals(-9.389151387171156 + 4.355172180607202.I, pow(w0, w2))

        for (w in listOf(ZERO, ONE, I, 3.R, w1, w0, INF, NaN)) {
            assertEquals(NaN, pow(INF, w))
            assertEquals(NaN, pow(NaN, w))
        }
    }

    @Test
    fun testPowZetaValues() {
        val w = 0.5 + 14.134725141734693790457251983562.I
        assertQuasiEquals(-1.3171414230751064 - 0.5149159850108396.I, pow(2, w))
        assertQuasiEquals(-1.7042590259602113 + 0.30903263975372064.I, pow(3, w))
        assertQuasiEquals(1.4697230567606336 + 1.356434346522595.I, pow(4, w))
        assertQuasiEquals(-1.6241462732233494 - 1.536928392338012.I, pow(5, w))
        assertQuasiEquals(-6.366647462900551 + 7.711407140278745.I, pow(100, w))
    }

}