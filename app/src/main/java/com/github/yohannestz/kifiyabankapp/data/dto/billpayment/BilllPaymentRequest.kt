package com.github.yohannestz.kifiyabankapp.data.dto.billpayment

import kotlinx.serialization.Serializable

@Serializable
data class BillPaymentRequest(
    val accountNumber: String,
    val biller: String,
    val amount: Double
)