package com.github.yohannestz.kifiyabankapp.data.dto.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val username: String,
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)