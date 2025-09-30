package com.github.yohannestz.kifiyabankapp.data.remote.api.accounts

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

class AccountsApiServiceImpl(private val client: HttpClient) : AccountsApiService {

    override suspend fun getAccounts(pageable: Pageable, accountNumber: String?): Result<Any> =
        runCatching {
            client.get("/api/accounts") {
                contentType(ContentType.Application.Json)
                parameters {
                    append("page", pageable.page.toString())
                    append("size", pageable.size.toString())
                    pageable.sort.forEach { append("sort", it) }
                    accountNumber?.let { append("accountNumber", it) }
                }
            }.body()
        }

    override suspend fun getAccountById(accountId: Long): Result<AccountResponse> = runCatching {
        client.get("/api/accounts/$accountId") {
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun createAccount(request: CreateAccountRequest): Result<AccountResponse> =
        runCatching {
            client.post("/api/accounts") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        }

    override suspend fun transfer(request: TransferRequest): Result<TransferResponse> = runCatching {
        client.post("/api/accounts/transfer") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun payBill(request: BillPaymentRequest): Result<BillPaymentResponse> =
        runCatching {
            client.post("/api/accounts/pay-bill") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        }

    override suspend fun getTransferDetails(transactionId: Long): Result<TransactionResponse> =
        runCatching {
            client.get("/api/accounts/transfer/$transactionId") {
                contentType(ContentType.Application.Json)
            }.body()
        }

    override suspend fun getBillPaymentDetails(transactionId: Long): Result<TransactionResponse> =
        runCatching {
            client.get("/api/accounts/pay-bill/$transactionId") {
                contentType(ContentType.Application.Json)
            }.body()
        }
}