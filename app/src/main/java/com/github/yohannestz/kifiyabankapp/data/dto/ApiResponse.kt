package com.github.yohannestz.kifiyabankapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null,
    val statusCode: Int
)