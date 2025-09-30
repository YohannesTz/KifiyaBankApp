package com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)