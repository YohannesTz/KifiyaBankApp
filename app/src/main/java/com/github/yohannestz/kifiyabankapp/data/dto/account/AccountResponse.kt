package com.github.yohannestz.kifiyabankapp.data.dto.account

import com.github.yohannestz.kifiyabankapp.data.model.AccountType
import com.github.yohannestz.kifiyabankapp.data.model.BankAccount
import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    val id: Long,
    val accountNumber: String,
    val balance: Double,
    val userId: Long,
    val accountType: AccountType
) {
    fun toBankAccount() = BankAccount(
        id = id,
        accountNumber = accountNumber,
        balance = balance,
        userId = userId,
        accountType = accountType
    )
}