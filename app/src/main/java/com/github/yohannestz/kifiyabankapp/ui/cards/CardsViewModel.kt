package com.github.yohannestz.kifiyabankapp.ui.cards

import androidx.lifecycle.viewModelScope
import com.github.yohannestz.kifiyabankapp.data.repository.accounts.AccountsRepository
import com.github.yohannestz.kifiyabankapp.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    override fun loadBills() {
        //No route was specified for bills on swagger docs
    }
}