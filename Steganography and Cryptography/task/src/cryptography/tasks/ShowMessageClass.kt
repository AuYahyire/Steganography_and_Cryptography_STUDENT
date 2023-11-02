package cryptography.tasks

import cryptography.tasks.Encrypter.toXOREncrypt
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class ShowMessageClass : TasksInterface {
    override fun executeTask() {
        try {
            println("Input image file: ")
            val inputURI = readln()
            println("Password:")
            val key = readln().toByteArray()
            // Read the input image from the provided URI.
            val image = ImageIO.read(File(inputURI))
            val extractedMessage = decodeMessage(image, key)
            println("Message:")
            println(extractedMessage)
        } catch (e: IOException) {
            println("Can't read input file!")
        }
    }

    override fun processImage(image: BufferedImage, outputImage: String, message: ByteArray, key: ByteArray) {
        TODO("Not yet implemented")
    }

    fun decodeMessage(inputImage: BufferedImage, key: ByteArray): String {
        val endMarker = byteArrayOf(0, 0, 3)
        var decodedMessage = byteArrayOf()
        var bytes = 0
        var bitPosition = 0
        var endOfMessage = false

        for (y in 0 until inputImage.height) {
            if (endOfMessage) {
                break
            }
            for (x in 0 until inputImage.width) {
                val pixels = inputImage.getRGB(x, y)
                val blue = Color(pixels).blue
                val bit = blue and 1
                bytes = (bytes.rotateLeft(1)) or bit
                bitPosition++

                if (bitPosition == 8) {
                    decodedMessage += bytes.toByte()
                    if (decodedMessage.takeLast(3).toByteArray().contentEquals(endMarker)) {
                        endOfMessage = true
                        break
                    }
                    bytes = 0
                    bitPosition = 0
                }
            }
        }

        return decodedMessage.toXOREncrypt(key).toString(Charsets.UTF_8).dropLast(3)
    }

}







