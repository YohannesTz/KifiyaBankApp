package com.github.yohannestz.kifiyabankapp.data.repository.accounts

import com.github.yohannestz.kifiyabankapp.data.dto.PageResponse
import com.github.yohannestz.kifiyabankapp.data.dto.Pageable
import com.github.yohannestz.kifiyabankapp.data.dto.account.AccountResponse
import com.github.yohannestz.kifiyabankapp.data.dto.account.CreateAccountRequest
import com.github.yohannestz.kifiyabankapp.data.dto.billpayment.BillPaymentRequest
import com.github.yohannestz.kifiyabankapp.data.dto.billpayment.BillPaymentResponse
import com.github.yohannestz.kifiyabankapp.data.dto.transaction.TransactionResponse
import com.github.yohannestz.kifiyabankapp.data.dto.transfer.TransferRequest
import com.github.yohannestz.kifiyabankapp.data.dto.transfer.TransferResponse
import com.github.yohannestz.kifiyabankapp.data.remote.api.accounts.AccountsApiService

class AccountsRepositoryImpl(
    private val service: AccountsApiService
) : AccountsRepository {

    override suspend fun getAccounts(pageable: Pageable?, accountNumber: String?): Result<PageResponse<AccountResponse>> =
        service.getAccounts(pageable, accountNumber)

    override suspend fun getAccountById(accountId: Long): Result<AccountResponse> =
        service.getAccountById(accountId)

    override suspend fun createAccount(request: CreateAccountRequest): Result<AccountResponse> =
        service.createAccount(request)

    override suspend fun transfer(request: TransferRequest): Result<TransferResponse> =
        service.transfer(request)

    override suspend fun payBill(request: BillPaymentRequest): Result<BillPaymentResponse> =
        service.payBill(request)

    override suspend fun getTransferDetails(transactionId: Long): Result<TransactionResponse> =
        service.getTransferDetails(transactionId)

    override suspend fun getBillPaymentDetails(transactionId: Long): Result<TransactionResponse> =
        service.getBillPaymentDetails(transactionId)
}