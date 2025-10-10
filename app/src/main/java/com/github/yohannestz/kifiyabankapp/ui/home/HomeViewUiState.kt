package com.github.yohannestz.kifiyabankapp.ui.home

import androidx.compose.runtime.Immutable
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.data.model.AccountType
import com.github.yohannestz.kifiyabankapp.data.model.BankAccount
import com.github.yohannestz.kifiyabankapp.data.model.Transaction
import com.github.yohannestz.kifiyabankapp.ui.base.state.TextEditorState
import com.github.yohannestz.kifiyabankapp.ui.base.state.UiState

@Immutable
data class HomeViewUiState(
    val deposit: TextEditorState<String> = TextEditorState("").apply {
        addCheck(
            { it.toDoubleOrNull()?.let { value -> value >= 10.0 } ?: false },
            R.string.error_deposit_too_low
        )
    },
    val selectedAccountType: AccountType? = null,
    val showAccountCreationDialog: Boolean = false,
    val accounts: List<BankAccount> = emptyList(),
    val recentTransactions: List<Transaction> = emptyList(),
    val isLoadingTransaction: Boolean = false,
    val isAddingAccount: Boolean = false,
    override val isLoading: Boolean = false,
    override val message: String? = null,
    override val messageId: Long? = null
): UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
    override fun setMessage(value: String?, messageId: Long) = copy(message = value, messageId = messageId)
}