package com.github.yohannestz.kifiyabankapp.ui.home

import androidx.compose.runtime.Immutable
import com.github.yohannestz.kifiyabankapp.data.model.BankAccount
import com.github.yohannestz.kifiyabankapp.data.model.Transaction
import com.github.yohannestz.kifiyabankapp.ui.base.state.UiState

@Immutable
data class HomeViewUiState(
    val accounts: List<BankAccount> = emptyList(),
    val recentTransactions: List<Transaction> = emptyList(),
    val isLoadingTransaction: Boolean = false,
    override val isLoading: Boolean = false,
    override val message: String? = null,
    override val messageId: Long? = null
): UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
    override fun setMessage(value: String?, messageId: Long) = copy(message = value, messageId = messageId)
}