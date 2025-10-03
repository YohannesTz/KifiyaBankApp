package com.github.yohannestz.kifiyabankapp.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions", indices = [Index("accountId"), Index("timestamp")]
)
data class TransactionEntity(
    @PrimaryKey val id: Long,
    val amount: Double,
    val type: String,
    val direction: String,
    val timestamp: String,
    val description: String?,
    val relatedAccount: String?,
    val accountId: Long,
    val lastUpdated: Long = System.currentTimeMillis()
)
