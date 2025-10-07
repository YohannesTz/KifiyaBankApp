package com.github.yohannestz.kifiyabankapp.data.remote.api.transactions

import com.github.yohannestz.kifiyabankapp.data.dto.PageTransactionResponse
import com.github.yohannestz.kifiyabankapp.data.dto.Pageable
import com.github.yohannestz.kifiyabankapp.data.remote.network.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters

class TransactionsApiServiceImpl(private val client: HttpClient) : TransactionsApiService {

    override suspend fun getAccountTransactions(
        accountId: Long,
        pageable: Pageable
    ): Result<PageTransactionResponse> = safeApiCall {
        client.get("/api/transactions/$accountId") {
            contentType(ContentType.Application.Json)
            parameters {
                append("page", pageable.page.toString())
                append("size", pageable.size.toString())
                pageable.sort.forEach { append("sort", it) }
            }
        }
    }
}