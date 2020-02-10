package org.kotlincook.math

import java.awt.Color
import java.awt.FlowLayout
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel


/**
 * number of iterations to check if z0 is in Mandelbrot set
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
    val n = 512 // create n x n image
    val bufferedImage = BufferedImage(n, n, TYPE_INT_RGB)
    val xc = -0.5
    val yc = 0.0
    val size = 2.0
    val max = 255 // maximum number of iterations

    for (i in 0 until n) {
        for (j in 0 until n) {
            val x0 = xc - size / 2 + size * i / n
            val y0 = yc - size / 2 + size * j / n
            val z0 = complexOf(x0, y0)
            val gray = max - mand(z0, max)
            val color = Color(gray/2, gray*gray%256, gray)
            bufferedImage.setRGB(i, j, color.rgb)
        }
    }
    val frame = JFrame()
    frame.getContentPane().setLayout(FlowLayout())
    frame.contentPane.add(JLabel(ImageIcon(bufferedImage)))
    frame.pack()
    frame.isVisible = true
}