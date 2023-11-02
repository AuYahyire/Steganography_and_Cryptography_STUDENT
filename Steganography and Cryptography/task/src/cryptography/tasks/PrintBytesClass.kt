package cryptography.tasks

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class PrintBytesClass {

    fun print() {
        println("Input image:")
        val image = readln()
        val imageRead = ImageIO.read(File(image))
        val firstBytes = read(imageRead)
        // val binaryToChar = binaryToChar(firstBytes)
        println(firstBytes)
        //println(binaryToChar)

    }

    fun read(imageRead: BufferedImage): List<String> {
        val bluePixels = mutableListOf<String>()

        for (x in 0 until 10) {
            for (y in 0 until 10) {
                val color = Color(imageRead.getRGB(x, y))
                val blueValue = color.blue

                // Convert the blue value to binary
                val binaryValue = Integer.toBinaryString(blueValue)
                bluePixels.add(blueValue.toString())
            }
        }

        return bluePixels
    }

    fun binaryToChar(binaryValues: List<String>): String {
        val charList = mutableListOf<Char>()

        for (binaryValue in binaryValues) {
            // Parse the binary string to an integer
            val decimalValue = Integer.parseInt(binaryValue, 2)

            // Convert the decimal value to a character
            val charValue = decimalValue.toChar()

            charList.add(charValue)
        }

        // Combine the characters into a single string
        charList.add((Integer.parseInt("01001000",2).toChar()))
        val resultString = charList.joinToString(separator = "")

        return resultString
    }
}