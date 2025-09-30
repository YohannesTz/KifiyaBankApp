package com.github.yohannestz.kifiyabankapp.data.dto.transaction

import com.github.yohannestz.kifiyabankapp.data.model.TransactionDirection
import com.github.yohannestz.kifiyabankapp.data.model.TransactionType
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    val id: Long,
    val amount: Double,
    val type: TransactionType,
    val direction: TransactionDirection,
    val timestamp: String,
    val description: String? = null,
    val relatedAccount: String? = null,
    val accountId: Long
)