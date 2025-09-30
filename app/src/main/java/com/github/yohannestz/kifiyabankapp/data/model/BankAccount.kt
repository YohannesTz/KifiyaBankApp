package com.github.yohannestz.kifiyabankapp.data.model

data class BankAccount(
    val id: Long,
    val accountNumber: String,
    val balance: Double,
    val userId: Long,
    val accountType: AccountType
)
