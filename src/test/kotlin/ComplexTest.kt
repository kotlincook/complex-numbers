package org.kotlincook.math

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
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
        assertEquals(Complex(input), Complex(expRe, expIm))
    }


    @Test
    fun testToPolarToComplex() {
        val z = 2.0 + 3.0.i
        assertEquals(z, z.toPolar().toComplex())
    }

    @Test
    fun testPolar() {
        assertTrue(mod(i - exp((π / 2).i)) < eps)
    }

    @Test
    fun testEulerEquation() {
        val z = "2.0+3.0i".toComplex()
        assertTrue(mod(cos(z) + i * sin(z) - exp(i * z)) < eps)
    }

    @Test
    fun testMod() {
        val z = "2.0+3.0i".toComplex()
        val r = mod(z)
        assertTrue(mod(!z*z - r*r) < eps)
    }

    @Test
    fun testConstants() {
        val z = "2.0+3.0i".toComplex()
        assertEquals(z * zero, zero)
        assertEquals(z * one, z)
    }

}