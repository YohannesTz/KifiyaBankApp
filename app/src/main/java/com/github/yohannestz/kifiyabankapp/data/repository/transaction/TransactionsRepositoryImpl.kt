package com.github.yohannestz.kifiyabankapp.data.repository.transaction

import com.github.yohannestz.kifiyabankapp.data.dto.PageTransactionResponse
import com.github.yohannestz.kifiyabankapp.data.dto.Pageable
import com.github.yohannestz.kifiyabankapp.data.remote.api.transactions.TransactionsApiService

class TransactionsRepositoryImpl(
    private val service: TransactionsApiService
) : TransactionsRepository {

    override suspend fun getAccountTransactions(
        accountId: Long,
        pageable: Pageable
    ): Result<PageTransactionResponse> =
        service.getAccountTransactions(accountId, pageable)
}