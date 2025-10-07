package com.github.yohannestz.kifiyabankapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null,
    val statusCode: Int
)

@Serializable
data class ApiError(
    val timestamp: String? = null,
    val status: Int? = null,
    val error: String? = null,
    val code: String? = null,
    val message: String? = null,
    val path: String? = null
)

@Serializable
data class ApiErrorResponse(
    val timestamp: String? = null,
    val status: Int? = null,
    val error: String? = null,
    val code: String? = null,
    val message: String? = null,
    val path: String? = null
)

class ApiException(
    override val message: String?,
    val apiError: ApiErrorResponse? = null
) : Exception(message)
