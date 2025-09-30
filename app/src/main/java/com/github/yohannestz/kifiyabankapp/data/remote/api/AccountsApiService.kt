package com.github.yohannestz.kifiyabankapp.data.remote.api

import com.github.yohannestz.kifiyabankapp.data.dto.Pageable
import com.github.yohannestz.kifiyabankapp.data.dto.account.AccountResponse
import com.github.yohannestz.kifiyabankapp.data.dto.account.CreateAccountRequest
import com.github.yohannestz.kifiyabankapp.data.dto.billpayment.BillPaymentRequest
import com.github.yohannestz.kifiyabankapp.data.dto.billpayment.BillPaymentResponse
import com.github.yohannestz.kifiyabankapp.data.dto.transaction.TransactionResponse
import com.github.yohannestz.kifiyabankapp.data.dto.transfer.TransferRequest
import com.github.yohannestz.kifiyabankapp.data.dto.transfer.TransferResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters

class AccountsApiService(private val client: HttpClient) {

    suspend fun getAccounts(pageable: Pageable, accountNumber: String? = null): Result<Any> {
        return try {
            val response = client.get("/api/accounts") {
                contentType(ContentType.Application.Json)
                parameters {
                    append("page", pageable.page.toString())
                    append("size", pageable.size.toString())
                    pageable.sort.forEach { sort ->
                        append("sort", sort)
                    }
                    accountNumber?.let { append("accountNumber", it) }
                }
            }.body<Any>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAccountById(accountId: Long): Result<AccountResponse> {
        return try {
            val response = client.get("/api/accounts/$accountId") {
                contentType(ContentType.Application.Json)
            }.body<AccountResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createAccount(request: CreateAccountRequest): Result<AccountResponse> {
        return try {
            val response = client.post("/api/accounts") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<AccountResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun transfer(request: TransferRequest): Result<TransferResponse> {
        return try {
            val response = client.post("/api/accounts/transfer") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<TransferResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun payBill(request: BillPaymentRequest): Result<BillPaymentResponse> {
        return try {
            val response = client.post("/api/accounts/pay-bill") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<BillPaymentResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTransferDetails(transactionId: Long): Result<TransactionResponse> {
        return try {
            val response = client.get("/api/accounts/transfer/$transactionId") {
                contentType(ContentType.Application.Json)
            }.body<TransactionResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBillPaymentDetails(transactionId: Long): Result<TransactionResponse> {
        return try {
            val response = client.get("/api/accounts/pay-bill/$transactionId") {
                contentType(ContentType.Application.Json)
            }.body<TransactionResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}