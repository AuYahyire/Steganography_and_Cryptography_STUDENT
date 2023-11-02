package cryptography.tasks

import kotlin.experimental.xor

object Encrypter {
    /**
     * Performs XOR encryption of this ByteArray using the provided key ByteArray.
     *
     * @param key The key ByteArray for XOR encryption.
     * @return The encrypted ByteArray.
     */
    fun ByteArray.toXOREncrypt(key: ByteArray) : ByteArray {
        val result = ByteArray(size) // Create a new ByteArray to store the encrypted data
        var i = 0

        for (j in indices) {
            result[j] = this[j] xor key[i] // Perform XOR encryption and store the result in the new ByteArray
            i = (i + 1) % key.size // Cycle through the key if it's shorter than the data
        }

        return result // Return the encrypted ByteArray
    }
}