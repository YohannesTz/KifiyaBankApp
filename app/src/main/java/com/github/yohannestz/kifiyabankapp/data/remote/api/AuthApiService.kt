package com.github.yohannestz.kifiyabankapp.data.remote.api

import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginRequest
import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginResponse
import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenRequest
import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenResponse
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterRequest
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApiService(private val client: HttpClient) {

    suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = client.post("/api/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<RegisterResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = client.post("/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<LoginResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun refreshToken(request: RefreshTokenRequest): Result<RefreshTokenResponse> {
        return try {
            val response = client.post("/api/auth/refresh-token") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<RefreshTokenResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}