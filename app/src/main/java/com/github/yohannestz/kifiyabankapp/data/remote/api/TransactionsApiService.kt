package com.github.yohannestz.kifiyabankapp.data.remote.api

import com.github.yohannestz.kifiyabankapp.data.dto.PageTransactionResponse
import com.github.yohannestz.kifiyabankapp.data.dto.Pageable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters

class TransactionsApiService(private val client: HttpClient) {

    suspend fun getAccountTransactions(
        accountId: Long,
        pageable: Pageable
    ): Result<PageTransactionResponse> {
        return try {
            val response = client.get("/api/transactions/$accountId") {
                contentType(ContentType.Application.Json)
                parameters {
                    append("page", pageable.page.toString())
                    append("size", pageable.size.toString())
                    pageable.sort.forEach { sort ->
                        append("sort", sort)
                    }
                }
            }.body<PageTransactionResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}