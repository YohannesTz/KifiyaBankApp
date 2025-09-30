package com.github.yohannestz.kifiyabankapp.data.dto.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val message: String,
    val username: String,
    val userId: Long,
    val initialAccountNumber: String
)