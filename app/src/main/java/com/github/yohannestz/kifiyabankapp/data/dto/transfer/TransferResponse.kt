package com.github.yohannestz.kifiyabankapp.data.dto.transfer

import kotlinx.serialization.Serializable

@Serializable
data class TransferResponse(
    val message: String,
    val amount: Double,
    val fromAccountNumber: String,
    val toAccountNumber: String
)