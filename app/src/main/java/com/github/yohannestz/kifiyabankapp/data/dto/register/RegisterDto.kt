package com.github.yohannestz.kifiyabankapp.data.dto.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val passwordHash: String,
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val phoneNumber: String?
)