package com.github.yohannestz.kifiyabankapp.data.repository.auth

import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginRequest
import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginResponse
import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenRequest
import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenResponse
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterRequest
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterResponse
import com.github.yohannestz.kifiyabankapp.data.remote.api.auth.AuthApiService

class AuthRepositoryImpl(
    private val service: AuthApiService
) : AuthRepository {

    override suspend fun register(request: RegisterRequest): Result<RegisterResponse> =
        service.register(request)

    override suspend fun login(request: LoginRequest): Result<LoginResponse> =
        service.login(request)

    override suspend fun refreshToken(request: RefreshTokenRequest): Result<RefreshTokenResponse> =
        service.refreshToken(request)
}