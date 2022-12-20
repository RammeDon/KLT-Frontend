package com.klt.util

import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class UserToken(
    val token: String? = null
)

class UserSettingsSerializer(private val cryptoManager: CryptoManager) : Serializer<UserToken> {
    override val defaultValue: UserToken
        get() = UserToken()

    override suspend fun readFrom(input: InputStream): UserToken {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            return Json.decodeFromString(
                deserializer = UserToken.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserToken, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = UserToken.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}
