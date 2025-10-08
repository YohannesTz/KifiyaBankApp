package com.github.yohannestz.kifiyabankapp.data.remote.api.transactions

import com.github.yohannestz.kifiyabankapp.data.dto.PageObjectResponse
import com.github.yohannestz.kifiyabankapp.data.dto.transaction.TransactionResponse

interface TransactionsApiService {
    suspend fun getAccountTransactions(
        accountId: Long,
        page: Int = 0,
        size: Int = 20
    ): Result<PageObjectResponse<TransactionResponse>>
}