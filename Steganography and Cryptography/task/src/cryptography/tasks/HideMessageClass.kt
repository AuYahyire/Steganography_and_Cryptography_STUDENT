package cryptography.tasks

import cryptography.tasks.Encrypter.toXOREncrypt
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

/**
 * Class that implements the TasksInterface for hiding a message within an image using the least significant bit (LSB) method.
 */
class HideMessageClass : TasksInterface {

    /**
     * Executes the task to hide a message in an image.
     */
    override fun executeTask() {
        println("Input image file:")
        val inputURI = readln()
        println("Output image file:")
        val outputURI = readln()
        println("Message to hide:")
        val message = readln()
        println("Password:")
        val key = readln().toByteArray()

        try {
            val image = ImageIO.read(File(inputURI))
            val messageByteArray = message.encodeToByteArray()
            if (messageByteArray.size * 8 > image.width * image.height) {
                println("The input image is not large enough to hold this message.")
                return
            }
            println("Message saved in $outputURI image.")
            processImage(image, outputURI, messageByteArray, key)
        } catch (e: IOException) {
            println("Can't read input file!")
        }
    }


    override fun processImage(image: BufferedImage, outputImage: String, messageByteArray: ByteArray, key: ByteArray) {
        var coordinateX = 0
        var coordinateY = 0

        val messageByteArrayEncrypted = messageByteArray.toXOREncrypt(key)
        val messageByteArrayEncryptedAndAppended = appendMessageBytes(messageByteArrayEncrypted)

        for (element in messageByteArrayEncryptedAndAppended) {
            for (bitPlace in 7 downTo 0) {
                val pixel = image.getRGB(coordinateX, coordinateY)
                val red = (pixel shr 16) and 0xFF
                val green = (pixel shr 8) and 0xFF
                val blue = pixel and 0xFF
                val byte = element.toInt()
                val bit = (byte shr bitPlace) and 1

                when (bit) {
                    0 -> {
                        val newBlue = blue and 0xFE
                        val newPixel = (red shl 16) or (green shl 8) or newBlue
                        image.setRGB(coordinateX, coordinateY, newPixel)
                    }

                    1 -> {
                        val newBlue = (blue and 0xFE) or bit
                        val newPixel = (red shl 16) or (green shl 8) or newBlue
                        image.setRGB(coordinateX, coordinateY, newPixel)
                    }
                }
                coordinateX = updateCoordinateX(image.width, coordinateX)
                coordinateY = updateCoordinateY(image.width, coordinateX, coordinateY)
            }
        }

        ImageIO.write(image, "PNG", File(outputImage))
    }

    private fun appendMessageBytes(message: ByteArray): ByteArray {
        var messageByteArray = message
        messageByteArray += "0".toByte()
        messageByteArray += "0".toByte()
        messageByteArray += "3".toByte()
        return messageByteArray
    }

    private fun updateCoordinateX(imageWidth: Int, currentCoordinateX: Int): Int {
        return if (currentCoordinateX <= imageWidth) currentCoordinateX + 1 else 0
    }

    private fun updateCoordinateY(imageWidth: Int, currentCoordinateX: Int, currentCoordinateY: Int): Int {
        return if (currentCoordinateX <= imageWidth) currentCoordinateY else currentCoordinateY + 1
    }
}


