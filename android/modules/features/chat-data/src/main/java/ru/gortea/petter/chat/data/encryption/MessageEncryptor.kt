package ru.gortea.petter.chat.data.encryption

import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

class MessageEncryptor(
    private val encryptKey: String
) {

    private val cipher = Cipher.getInstance(ALGORITHM).apply {
        val decodedKey = Base64.getDecoder().decode(encryptKey)
        val key = X509EncodedKeySpec(decodedKey)
        val keyFactory = KeyFactory.getInstance("RSA")

        init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(key))
    }

    fun encrypt(message: String): String {
        val secretMessageBytes = message.toByteArray(StandardCharsets.UTF_8)
        val encryptedMessageBytes = cipher.doFinal(secretMessageBytes)

        return Base64.getEncoder().encodeToString(encryptedMessageBytes)
    }

    private companion object {
        private const val ALGORITHM = "RSA/ECB/OAEPPadding"
    }
}
