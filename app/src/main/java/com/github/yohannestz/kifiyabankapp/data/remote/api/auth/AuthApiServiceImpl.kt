package com.github.yohannestz.kifiyabankapp.data.remote.api.auth

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

class AuthApiServiceImpl(private val client: HttpClient) : AuthApiService {

    override suspend fun register(request: RegisterRequest): Result<RegisterResponse> = runCatching {
        client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun login(request: LoginRequest): Result<LoginResponse> = runCatching {
        client.post("/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun refreshToken(request: RefreshTokenRequest): Result<RefreshTokenResponse> =
        runCatching {
            client.post("/api/auth/refresh-token") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        }
}
