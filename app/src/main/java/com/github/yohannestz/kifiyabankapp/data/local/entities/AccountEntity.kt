package com.github.yohannestz.kifiyabankapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.yohannestz.kifiyabankapp.data.model.AccountType
import com.github.yohannestz.kifiyabankapp.data.model.BankAccount

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: Long,
    val accountNumber: String,
    val balance: Double,
    val userId: Long,
    val accountType: String,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    fun toBankAccount() = BankAccount(
        id = id,
        accountNumber = accountNumber,
        balance = balance,
        userId = userId,
        accountType = AccountType.valueOf(accountType.uppercase())
    )
}