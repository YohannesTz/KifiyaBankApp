package com.github.yohannestz.kifiyabankapp.data.repository.auth

import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginRequest
import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginResponse
import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenRequest
import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenResponse
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterRequest
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterResponse
import com.github.yohannestz.kifiyabankapp.data.local.dao.UserDao
import com.github.yohannestz.kifiyabankapp.data.local.entities.UserEntity
import com.github.yohannestz.kifiyabankapp.data.remote.api.auth.AuthApiService
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val service: AuthApiService,
    private val userDao: UserDao
) : AuthRepository {

    override suspend fun register(request: RegisterRequest): Result<RegisterResponse> =
        service.register(request)

    override suspend fun login(request: LoginRequest): Result<LoginResponse> =
        service.login(request)

    override suspend fun refreshToken(request: RefreshTokenRequest): Result<RefreshTokenResponse> =
        service.refreshToken(request)

    override suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override suspend fun upsertUser(user: UserEntity) {
        userDao.upsertUser(user)
    }

    override suspend fun getCurrentUser(): UserEntity? {
        return userDao.getFirstUser()
    }

    override suspend fun getCurrentUserFlow(): Flow<UserEntity?> {
        return userDao.getFirstUserAsFlow()
    }

    override suspend fun clearUserData() {
        return userDao.clearUsers()
    }
}