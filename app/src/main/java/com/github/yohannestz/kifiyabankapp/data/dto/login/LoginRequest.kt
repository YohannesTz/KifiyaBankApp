package com.github.yohannestz.kifiyabankapp.data.dto.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val passwordHash: String
)
