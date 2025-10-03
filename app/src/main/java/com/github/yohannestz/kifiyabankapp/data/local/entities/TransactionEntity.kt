package com.github.yohannestz.kifiyabankapp.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.github.yohannestz.kifiyabankapp.data.model.Transaction
import com.github.yohannestz.kifiyabankapp.data.model.TransactionDirection
import com.github.yohannestz.kifiyabankapp.data.model.TransactionType
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

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
) {
    fun toTransaction() = Transaction(
        id = id,
        amount = amount,
        type = TransactionType.valueOf(type.uppercase()),
        direction = TransactionDirection.valueOf(direction.uppercase()),
        timestamp = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME),
        description = description,
        relatedAccount = relatedAccount,
        accountId = accountId
    )
}
