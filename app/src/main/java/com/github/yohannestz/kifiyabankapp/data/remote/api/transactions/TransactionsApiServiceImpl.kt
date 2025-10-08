package com.github.yohannestz.kifiyabankapp.data.remote.api.transactions

import com.github.yohannestz.kifiyabankapp.data.dto.PageObjectResponse
import com.github.yohannestz.kifiyabankapp.data.dto.transaction.TransactionResponse
import com.github.yohannestz.kifiyabankapp.data.remote.network.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TransactionsApiServiceImpl(private val client: HttpClient) : TransactionsApiService {
    override suspend fun getAccountTransactions(
        accountId: Long,
        page: Int,
        size: Int
    ): Result<PageObjectResponse<TransactionResponse>> = safeApiCall {
        client.get("/api/transactions/$accountId") {
            contentType(ContentType.Application.Json)
            parameter("page", page)
            parameter("size", size)
        }.body()
    }
}