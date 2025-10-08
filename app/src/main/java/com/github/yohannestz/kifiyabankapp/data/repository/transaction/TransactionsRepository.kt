package com.github.yohannestz.kifiyabankapp.data.repository.transaction

import com.github.yohannestz.kifiyabankapp.data.model.Transaction

interface TransactionsRepository {
    suspend fun getTransactions(accountId: Long, page: Int = 0, size: Int = 20): Result<List<Transaction>>
}