package cryptography.tasks

import java.awt.image.BufferedImage

/**
 * An interface that defines the structure of tasks to be executed.
 */
interface TasksInterface {

    /**
     * Executes the task, which typically involves reading user input and displaying a message.
     */
    fun executeTask()

    /**
     * Processes an image, potentially modifying it, and saves the result to an output image.
     *
     * @param image The input BufferedImage to process.
     * @param outputImage The URI of the output image file where the modified image will be saved.
     */
    fun processImage(image: BufferedImage, outputImage: String, messageByteArray: ByteArray, key: ByteArray)
}
