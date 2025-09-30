package com.github.yohannestz.kifiyabankapp.data.dto.billpayment

import kotlinx.serialization.Serializable

@Serializable
data class BillPaymentResponse(
    val message: String,
    val amount: Double,
    val accountNumber: String,
    val biller: String
)