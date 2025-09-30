package com.github.yohannestz.kifiyabankapp.data.model

import com.github.yohannestz.kifiyabankapp.data.dto.transaction.TransactionResponse
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

data class Transaction(
    val id: Long,
    val amount: Double,
    val type: TransactionType,
    val direction: TransactionDirection,
    val timestamp: LocalDateTime,
    val description: String?,
    val relatedAccount: String?,
    val accountId: Long
) {
    companion object {
        fun fromResponse(response: TransactionResponse): Transaction {
            return Transaction(
                id = response.id,
                amount = response.amount,
                type = response.type,
                direction = response.direction,
                timestamp = LocalDateTime.parse(
                    response.timestamp,
                    DateTimeFormatter.ISO_DATE_TIME
                ),
                description = response.description,
                relatedAccount = response.relatedAccount,
                accountId = response.accountId
            )
        }
    }
}
