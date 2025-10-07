package com.github.yohannestz.kifiyabankapp.data.remote.network

import com.github.yohannestz.kifiyabankapp.data.dto.ApiErrorResponse
import com.github.yohannestz.kifiyabankapp.data.dto.ApiException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

suspend inline fun <reified T> safeApiCall(
    crossinline apiCall: suspend () -> HttpResponse
): Result<T> {
    return try {
        val response = apiCall()
        val bodyText = response.bodyAsText()

        if (response.status.isSuccess()) {
            val data = Json.decodeFromString<T>(bodyText)
            Result.success(data)
        } else {
            val error = try {
                Json.decodeFromString(ApiErrorResponse.serializer(), bodyText)
            } catch (_: SerializationException) {
                ApiErrorResponse(message = bodyText)
            }
            Result.failure(ApiException(error.message ?: "Unknown error", error))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

