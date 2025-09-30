package com.github.yohannestz.kifiyabankapp.data.remote.api.auth

import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginRequest
import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginResponse
import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenRequest
import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenResponse
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterRequest
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterResponse

interface AuthApiService {
    suspend fun register(request: RegisterRequest): Result<RegisterResponse>
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun refreshToken(request: RefreshTokenRequest): Result<RefreshTokenResponse>
}
