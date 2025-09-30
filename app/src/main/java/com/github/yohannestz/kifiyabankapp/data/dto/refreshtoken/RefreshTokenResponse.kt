package com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val message: String,
    val accessToken: String,
    val refreshToken: String
)