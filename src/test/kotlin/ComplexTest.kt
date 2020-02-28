package org.kotlincook.math

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.lang.Math.PI
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ComplexTest {
    val eps = 1E-10

    @ParameterizedTest()
    @CsvSource(value =
    ["i;0.0;1.0", "-i;0.0;-1.0", "1.0+i;1.0;1.0", "1.0-i;1.0;-1.0", "-2.0+i;-2.0;1.0", "-2.0-i;-2.0;-1.0",
        "2.0+3.0i;2.0;3.0", "-2.0+3.0i;-2.0;3.0", "2.0-3.0i;2.0;-3.0", "-2.0-3.0i;-2.0;-3.0", "2.0;2.0;0.0",
        "-2.0;-2.0;0.0", "2.0i;0.0;2.0", "-2.0i;0.0;-2.0"
    ], delimiter = ';')
    fun testParsing(input: String, expRe: Double, expIm: Double) {
        assertEquals(input.toComplex(), complexOf(expRe, expIm))
    }

    @Test
    fun testIBuilder() {
        assertEquals(2.0 + 3.0.I, complexOf(2.0, 3.0))
        assertEquals(2.0 + 3.0 * I, complexOf(2.0, 3.0))
    }


    @Test
    fun testPolar() {
        assertTrue((I - exp((PI / 2).I)).mod < eps)
    }

    @Test
    fun testEulerEquation() {
        val z = "2.0+3.0I".toComplex()
        assertTrue((cos(z) + I * sin(z) - exp(I * z)).mod < eps)
    }

    @Test
    fun testMod() {
        val z = "2.0+3.0i".toComplex()
        val r = z.mod
        assertTrue((!z * z - r * r).mod < eps)
    }

    @Test
    fun testConstants() {
        val z = "2.0+3.0i".toComplex()
        assertEquals(z * ZERO, ZERO)
        assertEquals(z * ONE, z)
    }


    @Test
    fun testComplexAddAndSub() {
        val z0 = 2.0+3.0.I
        assertEquals(z0,ZERO + z0)
        assertEquals(-z0,ZERO - z0)
        val testData = listOf(
                Triple(INF, z0, INF),
                Triple(NaN, z0, NaN),
                Triple(z0, ZERO, z0),
                Triple(ZERO, ZERO, ZERO),
                Triple(INF, ZERO, INF),
                Triple(NaN, ZERO, NaN),
                Triple(z0, INF, INF),
                Triple(ZERO, INF, INF),
                Triple(INF, INF, NaN),
                Triple(NaN, INF, NaN),
                Triple(z0, NaN, NaN),
                Triple(ZERO, NaN, NaN),
                Triple(INF, NaN, NaN),
                Triple(NaN, NaN, NaN)
        )
        for (t in testData) {
            assertEquals(t.third,t.first + t.second,"${t.first} + ${t.second}")
            assertEquals(t.third,t.first - t.second, "${t.first} - ${t.second}")
        }
    }

    @Test
    fun testDoubleAddAndSub() {
        val z0 = 2.0+3.0.I
        val d0 = 3.0
        assertEquals(complexOf(d0, 0.0),ZERO + d0)
        assertEquals(complexOf(-d0, 0.0),ZERO - d0)
        assertEquals(complexOf(5.0, 3.0),z0 + d0)
        assertEquals(complexOf(-1.0, 3.0),z0 - d0)

        val testData = listOf(
                Triple(INF, d0, INF),
                Triple(NaN, d0, NaN),
                Triple(ZERO, 0.0, ZERO),
                Triple(INF, 0.0, INF),
                Triple(NaN, 0.0, NaN),
                Triple(z0, Double.POSITIVE_INFINITY, INF),
                Triple(ZERO, Double.POSITIVE_INFINITY, INF),
                Triple(INF, Double.POSITIVE_INFINITY, NaN),
                Triple(NaN, Double.POSITIVE_INFINITY, NaN),
                Triple(z0, Double.NEGATIVE_INFINITY, INF),
                Triple(ZERO, Double.NEGATIVE_INFINITY, INF),
                Triple(INF, Double.NEGATIVE_INFINITY, NaN),
                Triple(NaN, Double.NEGATIVE_INFINITY, NaN),
                Triple(z0, Double.NaN, NaN),
                Triple(ZERO, Double.NaN, NaN),
                Triple(INF, Double.NaN, NaN),
                Triple(NaN, Double.NaN, NaN)
        )
        for (t in testData) {
            assertEquals(t.third,t.first + t.second, "${t.first} + ${t.second}")
            assertEquals(t.third,t.first - t.second, "${t.first} - ${t.second}")
            assertEquals(t.third,t.second + t.first, "${t.second} + ${t.first}")
            assertEquals(-t.third,t.second - t.first, "${t.second} - ${t.first}")
        }
    }

    @Test
    fun testComplexMultiplication() {
        val z0 = 2.0+3.0.I
        val testData = listOf(
                Triple(ZERO, z0, ZERO),
                Triple(INF, z0, INF),
                Triple(NaN, z0, NaN),
                Triple(z0, ZERO, ZERO),
                Triple(ZERO, ZERO, ZERO),
                Triple(INF, ZERO, NaN),
                Triple(NaN, ZERO, NaN),
                Triple(z0, INF, INF),
                Triple(ZERO, INF, NaN),
                Triple(INF, INF, INF),
                Triple(NaN, INF, NaN),
                Triple(z0, NaN, NaN),
                Triple(ZERO, NaN, NaN),
                Triple(INF, NaN, NaN),
                Triple(NaN, NaN, NaN)
        )
        for (t in testData) {
            assertEquals(t.third,t.first * t.second, "${t.first} * ${t.second}")
        }
    }

    @Test
    fun testDoubleMultiplication() {
        val z0 = 3.0+3.0.I
        val d0 = 3.0
        val w0 = complexOf(9.0, 9.0)
        val testData = listOf(
                Triple(z0, d0, w0),
                Triple(ZERO, d0, ZERO),
                Triple(INF, d0, INF),
                Triple(NaN, d0, NaN),
                Triple(z0, 0.0, ZERO),
                Triple(ZERO, 0.0, ZERO),
                Triple(INF, 0.0, NaN),
                Triple(NaN, 0.0, NaN),
                Triple(z0, Double.POSITIVE_INFINITY, INF),
                Triple(ZERO, Double.POSITIVE_INFINITY, NaN),
                Triple(INF, Double.POSITIVE_INFINITY, INF),
                Triple(NaN, Double.POSITIVE_INFINITY, NaN),
                Triple(z0, Double.NEGATIVE_INFINITY, INF),
                Triple(ZERO, Double.NEGATIVE_INFINITY, NaN),
                Triple(INF, Double.NEGATIVE_INFINITY, INF),
                Triple(NaN, Double.NEGATIVE_INFINITY, NaN),
                Triple(z0, Double.NaN, NaN),
                Triple(ZERO, Double.NaN, NaN),
                Triple(INF, Double.NaN, NaN),
                Triple(NaN, Double.NaN, NaN)
        )
        for (t in testData) {
            assertEquals(t.third,t.first * t.second, "${t.first} * ${t.second}")
            assertEquals(t.third,t.second * t.first, "${t.second} + ${t.first}")
        }
    }

    @Test
    fun testComplexDivision() {
        val z0 = 2.0+3.0.I
        val testData = listOf(
                Triple(z0, z0, ONE),
                Triple(ZERO, z0, ZERO),
                Triple(INF, z0, INF),
                Triple(NaN, z0, NaN),
                Triple(z0, ZERO, INF),
                Triple(ZERO, ZERO, NaN),
                Triple(INF, ZERO, INF),
                Triple(NaN, ZERO, NaN),
                Triple(z0, INF, ZERO),
                Triple(ZERO, INF, ZERO),
                Triple(INF, INF, NaN),
                Triple(NaN, INF, NaN),
                Triple(z0, NaN, NaN),
                Triple(ZERO, NaN, NaN),
                Triple(INF, NaN, NaN),
                Triple(NaN, NaN, NaN)
        )
        for (t in testData) {
            assertEquals(t.third,t.first / t.second, "${t.first} / ${t.second}")
        }
    }

    @Test
    fun testDoubleDivision() {
        val z0 = 3.0+3.0.I
        val d0 = 3.0
        val w0 = complexOf(1.0, 1.0)
        val testData = listOf(
                Triple(z0, d0, w0),
                Triple(ZERO, d0, ZERO),
                Triple(INF, d0, INF),
                Triple(NaN, d0, NaN),
                Triple(z0, 0.0, INF),
                Triple(ZERO, 0.0, NaN),
                Triple(INF, 0.0, INF),
                Triple(NaN, 0.0, NaN),
                Triple(z0, Double.POSITIVE_INFINITY, ZERO),
                Triple(ZERO, Double.POSITIVE_INFINITY, ZERO),
                Triple(INF, Double.POSITIVE_INFINITY, NaN),
                Triple(NaN, Double.POSITIVE_INFINITY, NaN),
                Triple(z0, Double.NEGATIVE_INFINITY, ZERO),
                Triple(ZERO, Double.NEGATIVE_INFINITY, ZERO),
                Triple(INF, Double.NEGATIVE_INFINITY, NaN),
                Triple(NaN, Double.NEGATIVE_INFINITY, NaN),
                Triple(z0, Double.NaN, NaN),
                Triple(ZERO, Double.NaN, NaN),
                Triple(INF, Double.NaN, NaN),
                Triple(NaN, Double.NaN, NaN)
        )
        for (t in testData) {
            assertEquals(t.third,t.first / t.second, "${t.first} / ${t.second}")
        }
    }

    @Test
    fun testDoubleDivisionReverse() {
        val z0 = 3.0+3.0.I
        val d0 = 3.0
        val w0 = complexOf(0.5, -0.5)
        val testData = listOf(
                Triple(d0, z0, w0),
                Triple(d0, ZERO, INF),
                Triple(d0, INF, ZERO),
                Triple(d0, NaN, NaN),
                Triple(0.0, z0, ZERO),
                Triple(0.0, ZERO, NaN),
                Triple(0.0, INF, ZERO),
                Triple(0.0, NaN, NaN),
                Triple(Double.POSITIVE_INFINITY, z0, INF),
                Triple(Double.POSITIVE_INFINITY, ZERO, INF),
                Triple(Double.POSITIVE_INFINITY, INF, NaN),
                Triple(Double.POSITIVE_INFINITY, NaN, NaN),
                Triple(Double.NEGATIVE_INFINITY, z0, INF),
                Triple(Double.NEGATIVE_INFINITY, ZERO, INF),
                Triple(Double.POSITIVE_INFINITY, INF, NaN),
                Triple(Double.POSITIVE_INFINITY, NaN, NaN),
                Triple(Double.NaN, z0, NaN),
                Triple(Double.NaN, ZERO, NaN),
                Triple(Double.NaN, INF, NaN),
                Triple(Double.NaN, NaN, NaN)
        )
        for (t in testData) {
            assertEquals(t.third,t.first / t.second, "${t.first} / ${t.second}")
        }
    }

    @Test
    fun testToString() {
        assertEquals("0.0", complexOf(0.0, 0.0).toString())
        assertEquals("0.0", complexOf(-0.0, -0.0).toString())
        assertEquals("1.0", complexOf(1.0, 0.0).toString())
        assertEquals("-1.0", complexOf(-1.0, 0.0).toString())
        assertEquals("i", complexOf(0.0, 1.0).toString())
        assertEquals("-i", complexOf(0.0, -1.0).toString())
        assertEquals("1.0+i", complexOf(1.0, 1.0).toString())
        assertEquals("1.0-i", complexOf(1.0, -1.0).toString())
        assertEquals("3.0+2.0i", complexOf(3.0, 2.0).toString())
        assertEquals("3.0-2.0i", complexOf(3.0, -2.0).toString())
        assertEquals("-3.0+2.0i", complexOf(-3.0, 2.0).toString())
        assertEquals("-3.0-2.0i", complexOf(-3.0, -2.0).toString())
    }

}