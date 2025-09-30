package com.github.yohannestz.kifiyabankapp.data.model

data class User(
    val id: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String?,
    val phoneNumber: String?,
    val accessToken: String? = null,
    val refreshToken: String? = null
)