package com.github.yohannestz.kifiyabankapp.data.dto.transfer

import kotlinx.serialization.Serializable

@Serializable
data class TransferRequest(
    val fromAccountNumber: String,
    val toAccountNumber: String,
    val amount: Double
)