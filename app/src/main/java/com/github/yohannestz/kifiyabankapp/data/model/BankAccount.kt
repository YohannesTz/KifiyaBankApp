package com.github.yohannestz.kifiyabankapp.data.model

import com.github.yohannestz.kifiyabankapp.data.local.entities.AccountEntity

data class BankAccount(
    val id: Long,
    val accountNumber: String,
    val balance: Double,
    val userId: Long,
    val accountType: AccountType
) {
    fun toEntity() = AccountEntity(
        id = id,
        accountNumber = accountNumber,
        balance = balance,
        userId = userId,
        accountType = accountType.name,
        lastUpdated = System.currentTimeMillis()
    )
}
