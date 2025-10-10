package com.github.yohannestz.kifiyabankapp.ui.cards

import androidx.lifecycle.viewModelScope
import com.github.yohannestz.kifiyabankapp.data.dto.ApiException
import com.github.yohannestz.kifiyabankapp.data.dto.account.CreateAccountRequest
import com.github.yohannestz.kifiyabankapp.data.model.AccountType
import com.github.yohannestz.kifiyabankapp.data.repository.accounts.AccountsRepository
import com.github.yohannestz.kifiyabankapp.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class CardsViewModel(
    private val accountsRepository: AccountsRepository
): BaseViewModel<CardsViewUiState>(), CardsViewUiEvent {
    override val mutableUiState: MutableStateFlow<CardsViewUiState> =
        MutableStateFlow(CardsViewUiState())

    init {
        loadAccounts()
        loadBills()
    }

    override fun loadAccounts() {
        setLoading(true)
        viewModelScope.launch {
            accountsRepository.getLocalAccounts().fold(
                onSuccess = { accountEntityList ->
                    val backAccounts = accountEntityList.map { it.toBankAccount() }

                    mutableUiState.update {
                        it.copy(
                            accounts = backAccounts,
                            isLoading = false
                        )
                    }
                },
                onFailure = {

                }
            )
        }
    }

    override fun loadBills() {}

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
        Timber.e("creatAccount was clicked")
        setLoading(true)
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
                    setLoading(false)
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
                    setLoading(false)

                    mutableUiState.update {
                        it.copy(
                            isAddingAccount = false
                        )
                    }
                }
            )
        }
    }
}