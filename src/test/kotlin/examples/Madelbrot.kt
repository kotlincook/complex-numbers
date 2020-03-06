package org.kotlinmath.examples

import org.kotlinmath.Complex
import org.kotlinmath.complex
import java.awt.Color
import java.awt.FlowLayout
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import kotlin.system.measureTimeMillis


/**
 * Calculates the number of iterations from which the modulus of the iterated
 * value is greater than 2 or <code>max</code> if this is not the case after
 * <code>max</code> iterations
 * @return number of iterations
 */
fun mand(z0: Complex, max: Int): Int {
    var z = z0
    repeat(max) {
        if (z.mod > 2.0) return it
        z = z * z + z0
    }
    return max
}

fun main() {
    // ApacheComplex.activate()
    // LazyModArgComplex.activate()
    // CachingModArgComplex.activate()

    val n = 512 // creates an n x n image
    val bufferedImage = BufferedImage(n, n, TYPE_INT_RGB)
    val xc = -0.5
    val yc = 0.0
    val size = 2.0
    val max = 255 // maximum number of iterations

    val time = measureTimeMillis {
        for (i in 0 until n) {
            for (j in 0 until n) {
                val x0 = xc - size / 2 + size * i / n
                val y0 = yc - size / 2 + size * j / n
                val z0 = complex(x0, y0)
                val gray = max - mand(z0, max)
                val color = Color(gray / 2, gray * gray % 256, gray * gray * gray % 256)
                bufferedImage.setRGB(i, j, color.rgb)
            }
        }
    }
    println("Calculation time: $time")
    val frame = JFrame()
    frame.contentPane.setLayout(FlowLayout())
    frame.contentPane.add(JLabel(ImageIcon(bufferedImage)))
    frame.pack()
    frame.isVisible = true
}