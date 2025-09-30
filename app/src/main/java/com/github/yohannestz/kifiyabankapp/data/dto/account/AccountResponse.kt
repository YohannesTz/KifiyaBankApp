package com.github.yohannestz.kifiyabankapp.data.dto.account

import com.github.yohannestz.kifiyabankapp.data.model.AccountType
import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    val id: Long,
    val accountNumber: String,
    val balance: Double,
    val userId: Long,
    val accountType: AccountType
)