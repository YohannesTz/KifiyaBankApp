package com.github.yohannestz.kifiyabankapp.data.remote.api.transactions

import com.github.yohannestz.kifiyabankapp.data.dto.PageTransactionResponse
import com.github.yohannestz.kifiyabankapp.data.dto.Pageable

interface TransactionsApiService {
    suspend fun getAccountTransactions(
        accountId: Long,
        pageable: Pageable
    ): Result<PageTransactionResponse>
}