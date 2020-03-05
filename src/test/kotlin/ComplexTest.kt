package org.kotlinmath

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.lang.Math.PI
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue


const val EPS = 1E-13

class ComplexTest {

    private fun assertEquals(expected: Number, actual: Number, precision: Double, message: String? = null) =
            assertTrue(Math.abs(expected.toDouble() - actual.toDouble()) < precision,
                    message ?: "Expected <$expected>, actual <$actual>")

    private fun assertEquals(expected: Complex, actual: Complex, precision: Double, message: String? = null) =
            assertTrue((expected - actual).mod < precision,
                    message ?: "Expected <$expected>, actual <$actual>")


    @ParameterizedTest()
    @CsvSource(value =
    ["i;0.0;1.0", "-i;0.0;-1.0", "2i;0.0;2.0", "-2i;0.0;-2.0", "3 -2 i;3.0;-2.0",
        "1.0+i;1.0;1.0", "1.0-i;1.0;-1.0", "-2.0+i;-2.0;1.0", "-2.0-i;-2.0;-1.0", "2.0+3.0i;2.0;3.0",
        "-2.0+3.0I;-2.0;3.0", "2.0-3.0I;2.0;-3.0", "-2.0-3.0I;-2.0;-3.0", "2.0;2.0;0.0",
        "-2.0;-2.0;0.0", "2.0i;0.0;2.0", "-2.0i;0.0;-2.0", "2.0f+3.0fi;2.0;3.0",
        "0;0.0;0.0", "-0;0.0;0.0", "1;1.0;0.0", "-1;-1.0;0.0", "0-i;0.0;-1.0", "0+i;0.0;1.0"
    ], delimiter = ';')
    fun testToComplex(input: String, expRe: Double, expIm: Double) {
        assertEquals(input.toComplex(), complex(expRe, expIm))
    }

    @Test
    fun testToComplexSpecial() {
        assertEquals(INF, toComplex("Infinity"))
        assertEquals(NaN, toComplex("NaN"))
        assertThrows<NumberFormatException> { toComplex("") }
        assertThrows<NumberFormatException> { toComplex("falsch") }
        assertThrows<NumberFormatException> { toComplex("3+4j") }
        assertThrows<NumberFormatException> { toComplex("+-3") }
        assertThrows<NumberFormatException> { toComplex("2+-3i") }
        assertThrows<NumberFormatException> { toComplex("2+3i-3-5i") }
    }

    @Test
    fun testIBuilder() {
        assertEquals(2.0 + 3.0.I, complex(2.0, 3.0))
        assertEquals(2.0 + 3.0 * I, complex(2.0, 3.0))
    }


    @Test
    fun testPolar() {
        assertEquals(I, exp((PI / 2).I), EPS)
    }

    @Test
    fun testEulerEquation() {
        val z = "2.0+3.0I".toComplex()
        assertEquals(cos(z) + I * sin(z), exp(I * z), EPS)
    }

    @Test
    fun testMod() {
        val z = "2+3i".toComplex()
        val r = z.mod
        assertEquals((!z * z).re, r * r, EPS)
    }

    @Test
    fun testArg() {
        assertEquals(0.0, ZERO.arg)
        assertEquals(0.0, (5 * ONE).arg)
        assertEquals(0.5, exp(0.5 * I).arg, EPS)
        assertEquals(PI / 2, 3.I.arg, EPS)
        assertEquals(3, exp(3 * I).arg, EPS)
        assertEquals(PI, (-2.R).arg, EPS)
        assertEquals(4 - 2 * PI, exp(4 * I).arg, EPS)
        assertEquals(-PI / 2, (-3.I).arg, EPS)
        assertEquals(6 - 2 * PI, exp(6 * I).arg, EPS)
    }

    @Test
    fun testConstants() {
        val z = "2.0+3.0i".toComplex()
        assertEquals(z * ZERO, ZERO)
        assertEquals(z * ONE, z)
    }


    @Test
    fun testComplexAddAndSub() {
        val z0 = 2.0 + 3.0.I
        assertEquals(z0, ZERO + z0)
        assertEquals(-z0, ZERO - z0)
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
            assertEquals(t.third, t.first + t.second, "${t.first} + ${t.second}")
            assertEquals(t.third, t.first - t.second, "${t.first} - ${t.second}")
        }
    }

    @Test
    fun testDoubleAddAndSub() {
        val z0 = 2.0 + 3.0.I
        val d0 = 3.0
        assertEquals(complex(d0, 0.0), ZERO + d0)
        assertEquals(complex(-d0, 0.0), ZERO - d0)
        assertEquals(complex(5.0, 3.0), z0 + d0)
        assertEquals(complex(-1.0, 3.0), z0 - d0)

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
            assertEquals(t.third, t.first + t.second, "${t.first} + ${t.second}")
            assertEquals(t.third, t.first - t.second, "${t.first} - ${t.second}")
            assertEquals(t.third, t.second + t.first, "${t.second} + ${t.first}")
            assertEquals(-t.third, t.second - t.first, "${t.second} - ${t.first}")
        }
    }


    @Test
    fun testComplexMultiplication() {
        val z0 = 2.0 + 3.0.I
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
            assertEquals(t.third, t.first * t.second, "${t.first} * ${t.second}")
        }
    }

    @Test
    fun testDoubleMultiplication() {
        val z0 = 3.0 + 3.0.I
        val d0 = 3.0
        val w0 = complex(9.0, 9.0)
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
            assertEquals(t.third, t.first * t.second, "${t.first} * ${t.second}")
            assertEquals(t.third, t.second * t.first, "${t.second} + ${t.first}")
        }
    }

    @Test
    fun testComplexDivision() {
        val z0 = 2.0 + 3.0.I
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
            assertEquals(t.third, t.first / t.second, "${t.first} / ${t.second}")
        }
    }

    @Test
    fun testDoubleDivision() {
        val z0 = 3.0 + 3.0.I
        val d0 = 3.0
        val w0 = complex(1.0, 1.0)
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
            assertEquals(t.third, t.first / t.second, "${t.first} / ${t.second}")
        }
    }

    @Test
    fun testDoubleDivisionReverse() {
        val z0 = 3.0 + 3.0.I
        val d0 = 3.0
        val w0 = complex(0.5, -0.5)
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
            assertEquals(t.third, t.first / t.second, "${t.first} / ${t.second}")
        }
    }


    @Test
    fun testNumberAddSubMulAndDiv() {
        val z0 = 2 + 4.I
        assertEquals(4.I, z0 + (-2))
        assertEquals(4.I, z0 - 2)
        assertEquals(4 + 8.I, z0 * 2)
        assertEquals(1 + 2.I, z0 / 2)
    }

    @Test
    fun testConjugate() {
        val input = complex(3.0, 4.0)
        val expected = complex(3.0, -4.0)
        assertEquals(expected, input.conj())
        assertEquals(expected, !input)
        assertEquals(25.0, (input * !input).mod)
    }

    @Test
    fun testToString() {
        assertEquals("0.0", complex(0.0, 0.0).toString())
        assertEquals("0.0", complex(-0.0, -0.0).toString())
        assertEquals("1.0", complex(1.0, 0.0).toString())
        assertEquals("-1.0", complex(-1.0, 0.0).toString())
        assertEquals("i", complex(0.0, 1.0).toString())
        assertEquals("-i", complex(0.0, -1.0).toString())
        assertEquals("1.0+i", complex(1.0, 1.0).toString())
        assertEquals("1.0-i", complex(1.0, -1.0).toString())
        assertEquals("3.0+2.0i", complex(3.0, 2.0).toString())
        assertEquals("3.0-2.0i", complex(3.0, -2.0).toString())
        assertEquals("-3.0+2.0i", complex(-3.0, 2.0).toString())
        assertEquals("-3.0-2.0i", complex(-3.0, -2.0).toString())
    }

    @Test
    fun testEquals() {
        val z0 = complex(4, 5)
        val z1 = 4 + 5.I
        val four = complex(4, 0)
        val fiveI = 5.I
        assertEquals(z0, z1)
        assertNotEquals(z0, four)
        assertNotEquals(z0, fiveI)
        assertTrue(!four.equals(4L))
        assertTrue(!four.equals(4))
        assertTrue(!four.equals(4.0))
        assertTrue(!four.equals(4.0f))
        assertTrue(!four.equals(4.toBigDecimal()))
        assertTrue(!four.equals(4.toBigInteger()))
        assertTrue(!four.equals(""))
        assertTrue(four.equals("4".toComplex()))
    }

    @Test
    fun testHashCode() {
        val z0 = complex(4, 5)
        val z1 = 4 + 5.I
        val four = complex(4, 0)
        val fiveI = 5.I
        assertNotEquals(0, z0.hashCode())
        assertNotEquals(0, four.hashCode())
        assertNotEquals(0, fiveI.hashCode())
        assertEquals(z0.hashCode(), z1.hashCode())
    }

    @Test
    fun testConstructor() {
        val z0 = complex(4, 5)
        val z1 = DefaultComplex(z0)
        assertEquals(z0, z1)
        val z2 = DefaultComplex("4+5i")
        assertEquals(z0, z2)
    }

}