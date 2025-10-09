package com.github.yohannestz.kifiyabankapp.data.repository.auth

import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginRequest
import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginResponse
import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenRequest
import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenResponse
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterRequest
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterResponse
import com.github.yohannestz.kifiyabankapp.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(request: RegisterRequest): Result<RegisterResponse>
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun refreshToken(request: RefreshTokenRequest): Result<RefreshTokenResponse>

    suspend fun saveUser(user: UserEntity)
    suspend fun upsertUser(user: UserEntity)
    suspend fun getCurrentUser(): UserEntity?
    suspend fun getCurrentUserFlow(): Flow<UserEntity?>
    suspend fun clearUserData()
}