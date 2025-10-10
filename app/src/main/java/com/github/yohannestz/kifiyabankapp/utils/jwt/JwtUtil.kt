package com.github.yohannestz.kifiyabankapp.utils.jwt

import android.os.Build
import android.util.Base64
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.nio.charset.StandardCharsets

/**
 * Utility class for handling JWT tokens.
 */
object JwtUtil {
    private const val HEADER = 0
    private const val PAYLOAD = 1
    private const val SIGNATURE = 2
    private const val JWT_PARTS = 3

    private val json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Returns the payload of a JWT as a raw JsonObject.
     */
    @Throws(Exception::class)
    fun getPayload(jwt: String): JsonObject? {
        return try {
            validateJWT(jwt)
            val payload = jwt.split("\\.".toRegex()).toTypedArray()[PAYLOAD]
            val sectionDecoded = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                java.util.Base64.getDecoder().decode(payload)
            } else {
                Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            }
            val jwtSection = String(sectionDecoded, StandardCharsets.UTF_8)
            json.decodeFromString<JsonObject>(jwtSection)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Parses JWT payload into strongly typed [TokenClaims].
     */
    @Throws(Exception::class)
    fun getTokenClaims(jwt: String): TokenClaims? {
        return try {
            validateJWT(jwt)
            val payload = jwt.split("\\.".toRegex())[PAYLOAD]
            val sectionDecoded = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                java.util.Base64.getDecoder().decode(payload)
            } else {
                Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            }
            val jwtSection = String(sectionDecoded, StandardCharsets.UTF_8)
            json.decodeFromString<TokenClaims>(jwtSection)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Parses JWT payload into strongly typed [RefreshTokenClaims].
     */
    @Throws(Exception::class)
    fun getRefreshTokenClaims(jwt: String): RefreshTokenClaims? {
        return try {
            validateJWT(jwt)
            val payload = jwt.split("\\.".toRegex())[PAYLOAD]
            val sectionDecoded = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                java.util.Base64.getDecoder().decode(payload)
            } else {
                Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            }
            val jwtSection = String(sectionDecoded, StandardCharsets.UTF_8)
            json.decodeFromString<RefreshTokenClaims>(jwtSection)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Verifies that the JWT string has exactly three parts.
     */
    @Throws(Exception::class)
    fun validateJWT(jwt: String) {
        val jwtParts = jwt.split("\\.".toRegex())
        if (jwtParts.size != JWT_PARTS) {
            throw Exception("Invalid JWT format")
        }
    }
}

@Serializable
data class TokenClaims(
    val exp: Long,
    val `for`: String? = null,
    val iat: Long,
    val iss: String? = null,
    val jti: String? = null,
    val prm: String? = null,
    val rex: Long? = null,
    val rol: String? = null,
    val ses: String? = null,
    val sub: String? = null,
    val typ: String? = null
)

@Serializable
data class RefreshTokenClaims(
    val exp: Long,
    val `for`: String? = null,
    val iat: Long,
    val iss: String? = null,
    val jti: String? = null,
    val prm: String? = null,
    val rex: Long? = null,
    val rol: String? = null,
    val scp: List<String>? = null,
    val ses: String? = null,
    val sub: String? = null,
    val typ: String? = null
)
