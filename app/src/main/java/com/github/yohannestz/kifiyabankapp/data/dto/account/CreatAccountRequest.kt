package com.github.yohannestz.kifiyabankapp.data.dto.account

import com.github.yohannestz.kifiyabankapp.data.model.AccountType
import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequest(
    val accountType: AccountType,
    val initialBalance: Double
)