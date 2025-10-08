package com.github.yohannestz.kifiyabankapp.data.repository.transaction

import com.github.yohannestz.kifiyabankapp.data.model.Transaction
import com.github.yohannestz.kifiyabankapp.data.remote.api.transactions.TransactionsApiService

class TransactionsRepositoryImpl(
    private val service: TransactionsApiService
) : TransactionsRepository {
    override suspend fun getTransactions(accountId: Long, page: Int, size: Int): Result<List<Transaction>> =
        service.getAccountTransactions(accountId, page, size).map { response ->
            response.content.map { Transaction.fromResponse(it) }
    }
}