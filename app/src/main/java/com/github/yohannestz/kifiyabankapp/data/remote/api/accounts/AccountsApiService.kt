package com.github.yohannestz.kifiyabankapp.data.remote.api.accounts

import com.github.yohannestz.kifiyabankapp.data.dto.Pageable
import com.github.yohannestz.kifiyabankapp.data.dto.account.AccountResponse
import com.github.yohannestz.kifiyabankapp.data.dto.account.CreateAccountRequest
import com.github.yohannestz.kifiyabankapp.data.dto.billpayment.BillPaymentRequest
import com.github.yohannestz.kifiyabankapp.data.dto.billpayment.BillPaymentResponse
import com.github.yohannestz.kifiyabankapp.data.dto.transaction.TransactionResponse
import com.github.yohannestz.kifiyabankapp.data.dto.transfer.TransferRequest
import com.github.yohannestz.kifiyabankapp.data.dto.transfer.TransferResponse

interface AccountsApiService {
    suspend fun getAccounts(pageable: Pageable, accountNumber: String? = null): Result<Any>
    suspend fun getAccountById(accountId: Long): Result<AccountResponse>
    suspend fun createAccount(request: CreateAccountRequest): Result<AccountResponse>
    suspend fun transfer(request: TransferRequest): Result<TransferResponse>
    suspend fun payBill(request: BillPaymentRequest): Result<BillPaymentResponse>
    suspend fun getTransferDetails(transactionId: Long): Result<TransactionResponse>
    suspend fun getBillPaymentDetails(transactionId: Long): Result<TransactionResponse>
}