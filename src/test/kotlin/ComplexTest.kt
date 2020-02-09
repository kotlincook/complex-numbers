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
    ["2.0+3.0i;2.0;3.0", "-2.0+3.0i;-2.0;3.0", "2.0-3.0i;2.0;-3.0", "-2.0-3.0i;-2.0;-3.0",
        "2.0;2.0;0.0", "-2.0;-2.0;0.0", "2.0i;0.0;2.0", "-2.0i;0.0;-2.0"
    ], delimiter = ';')
    fun testParsing(input: String, expRe: Double, expIm: Double) {
        assertEquals(input.toComplex(), Complex(expRe, expIm))
    }


    @Test
    fun testToPolarToComplex() {
        val z = (2.0 + 3.0.I) as ComplexImpl
        assertEquals(z, z.toPolar().toComplex())
    }

    @Test
    fun testPolar() {
        assertTrue((I - exp((PI / 2).I)).mod < eps)
    }

    @Test
    fun testEulerEquation() {
        val z = "2.0+3.0i".toComplex()
        assertTrue((cos(z) + I * sin(z) - exp(I * z)).mod < eps)
    }

    @Test
    fun testMod() {
        val z = "2.0+3.0i".toComplex()
        val r = z.mod
        assertTrue((!z*z - r*r).mod < eps)
    }

    @Test
    fun testConstants() {
        val z = "2.0+3.0i".toComplex()
        assertEquals(z * ZERO, ZERO)
        assertEquals(z * ONE, z)
    }

}