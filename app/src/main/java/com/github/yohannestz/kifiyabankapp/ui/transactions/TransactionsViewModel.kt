package com.github.yohannestz.kifiyabankapp.ui.transactions

import androidx.lifecycle.viewModelScope
import com.github.yohannestz.kifiyabankapp.data.dto.ApiException
import com.github.yohannestz.kifiyabankapp.data.repository.accounts.AccountsRepository
import com.github.yohannestz.kifiyabankapp.data.repository.transaction.TransactionsRepository
import com.github.yohannestz.kifiyabankapp.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository
) : BaseViewModel<TransactionsViewUiState>(), TransactionsViewUiEvent {

    override val mutableUiState: MutableStateFlow<TransactionsViewUiState> =
        MutableStateFlow(TransactionsViewUiState())

    init {
        loadTransactions()
    }

    override fun loadTransactions() {
        viewModelScope.launch {
            setLoading(true)

            val firstAccountNumber = accountsRepository
                .getLocalAccounts().getOrNull()
                ?.firstOrNull()
                ?.accountNumber
                ?.toLongOrNull()

            if (firstAccountNumber == null) {
                showMessage("No valid account found")
                setLoading(false)
                return@launch
            }

            transactionsRepository.getTransactions(
                accountId = firstAccountNumber,
                page = 0,
                size = 50
            ).fold(
                onSuccess = { list ->
                    mutableUiState.update {
                        it.copy(
                            transactions = list,
                            isLoading = false
                        )
                    }
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

    override fun onFilterChanged(filter: TransactionFilter) {
        mutableUiState.update { it.copy(selectedFilter = filter) }
    }

    override fun onMessageDisplayed() {
        mutableUiState.update { it.copy(message = null) }
    }
}
