package com.github.yohannestz.kifiyabankapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: Long,
    val accountNumber: String,
    val balance: Double,
    val userId: Long,
    val accountType: String,
    val lastUpdated: Long = System.currentTimeMillis()
)