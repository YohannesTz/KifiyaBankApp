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
import com.github.yohannestz.kifiyabankapp.data.local.entities.AccountEntity
import com.github.yohannestz.kifiyabankapp.data.model.BankAccount
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    suspend fun getLocalAccounts(): Result<List<AccountEntity>>
    suspend fun getLocalAccountsFlow(): Result<Flow<List<AccountEntity>>>
    suspend fun getAccounts(pageable: Pageable? = null, accountNumber: String? = null): Result<PageResponse<AccountResponse>>
    suspend fun getAccountById(accountId: Long): Result<AccountResponse>
    suspend fun createAccount(request: CreateAccountRequest): Result<AccountResponse>
    suspend fun transfer(request: TransferRequest): Result<TransferResponse>
    suspend fun payBill(request: BillPaymentRequest): Result<BillPaymentResponse>
    suspend fun saveAccounts(accounts: List<BankAccount>)
    suspend fun getTransferDetails(transactionId: Long): Result<TransactionResponse>
    suspend fun getBillPaymentDetails(transactionId: Long): Result<TransactionResponse>
}