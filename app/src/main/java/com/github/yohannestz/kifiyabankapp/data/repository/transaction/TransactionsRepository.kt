package com.github.yohannestz.kifiyabankapp.data.repository.transaction

import com.github.yohannestz.kifiyabankapp.data.dto.PageTransactionResponse
import com.github.yohannestz.kifiyabankapp.data.dto.Pageable

interface TransactionsRepository {
    suspend fun getAccountTransactions(
        accountId: Long,
        pageable: Pageable
    ): Result<PageTransactionResponse>
}
