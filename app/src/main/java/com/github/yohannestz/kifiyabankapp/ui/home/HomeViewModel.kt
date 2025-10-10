package com.github.yohannestz.kifiyabankapp.ui.home

import androidx.lifecycle.viewModelScope
import com.github.yohannestz.kifiyabankapp.data.dto.ApiException
import com.github.yohannestz.kifiyabankapp.data.dto.account.CreateAccountRequest
import com.github.yohannestz.kifiyabankapp.data.model.AccountType
import com.github.yohannestz.kifiyabankapp.data.repository.accounts.AccountsRepository
import com.github.yohannestz.kifiyabankapp.data.repository.transaction.TransactionsRepository
import com.github.yohannestz.kifiyabankapp.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val accountsRepository: AccountsRepository,
    private val transactionsRepository: TransactionsRepository
) : BaseViewModel<HomeViewUiState>(), HomeViewUiEvent {
    override val mutableUiState: MutableStateFlow<HomeViewUiState> =
        MutableStateFlow(HomeViewUiState())

    init {
        loadAccounts()
    }

    override fun loadAccounts() {
        setLoading(true)

        viewModelScope.launch {
            accountsRepository.getAccounts().fold(
                onSuccess = { accountsResponse ->
                    val bankAccounts = accountsResponse.content.map { it.toBankAccount() }

                    mutableUiState.update {
                        it.copy(
                            accounts = bankAccounts,
                            isLoading = false
                        )
                    }

                    accountsRepository.saveAccounts(
                        bankAccounts
                    )

                    loadTransactions()
                },
                onFailure = { error ->
                    val message = when (error) {
                        is ApiException -> error.apiError?.message ?: error.message
                        else -> error.message ?: "Unknown error"
                    }

                    showMessage(message)
                    setLoading(false)
                }
            )
        }
    }

    override fun loadTransactions() {
        viewModelScope.launch {
            mutableUiState.update { it.copy(isLoadingTransaction = true) }

            val firstAccountNumber = mutableUiState.value.accounts.firstOrNull()?.accountNumber?.toLongOrNull()

            if (firstAccountNumber == null) {
                showMessage("No valid account found")
                mutableUiState.update { it.copy(isLoadingTransaction = false) }
                return@launch
            }

            transactionsRepository.getTransactions(
                accountId = firstAccountNumber,
                page = 0,
                size = 20
            ).fold(
                onSuccess = { transactionsResponse ->
                    mutableUiState.update {
                        it.copy(
                            recentTransactions = transactionsResponse,
                            isLoadingTransaction = false
                        )
                    }
                },
                onFailure = { error ->
                    val message = when (error) {
                        is ApiException -> error.apiError?.message ?: error.message
                        else -> error.message ?: "Unknown error"
                    }

                    showMessage(message)
                    mutableUiState.update { it.copy(isLoadingTransaction = false) }
                }
            )
        }
    }

    override fun showAccountCreationDialog(value: Boolean) {
       mutableUiState.update {
           it.copy(
               showAccountCreationDialog = value
           )
       }
    }

    override fun onAccountTypeSelected(value: AccountType) {
        mutableUiState.update {
            it.copy(
                selectedAccountType = value
            )
        }
    }

    override fun setDeposit(value: String) {
        mutableUiState.update {
            it.copy(
                deposit = it.deposit.getUpdatedState(value)
            )
        }
    }

    override fun createAccount() {
        mutableUiState.update {
            it.copy(
                isAddingAccount = true
            )
        }

        viewModelScope.launch {
            val createAccountRequest = CreateAccountRequest(
                accountType = mutableUiState.value.selectedAccountType ?: AccountType.SAVINGS,
                initialBalance = mutableUiState.value.deposit.current.toDouble()
            )

            accountsRepository.createAccount(createAccountRequest).fold(
                onSuccess = {
                    loadAccounts()
                    showAccountCreationDialog(false)
                    showMessage("Account was created successfully!")

                    mutableUiState.update {
                        it.copy(
                            isAddingAccount = false
                        )
                    }
                },
                onFailure = { error ->
                    val message = when (error) {
                        is ApiException -> error.apiError?.message ?: error.message
                        else -> error.message ?: "Unknown error"
                    }

                    showMessage(message)
                }
            )
        }
    }
}