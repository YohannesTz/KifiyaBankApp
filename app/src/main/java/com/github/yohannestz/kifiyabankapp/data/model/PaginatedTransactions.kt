package com.github.yohannestz.kifiyabankapp.data.model

import com.github.yohannestz.kifiyabankapp.data.dto.PageTransactionResponse

data class PaginatedTransactions(
    val transactions: List<Transaction>,
    val totalPages: Int,
    val totalElements: Long,
    val currentPage: Int,
    val pageSize: Int,
    val isFirst: Boolean,
    val isLast: Boolean
) {
    companion object {
        fun fromResponse(response: PageTransactionResponse): PaginatedTransactions {
            return PaginatedTransactions(
                transactions = response.content.map { Transaction.fromResponse(it) },
                totalPages = response.totalPages,
                totalElements = response.totalElements,
                currentPage = response.number,
                pageSize = response.size,
                isFirst = response.first,
                isLast = response.last
            )
        }
    }
}