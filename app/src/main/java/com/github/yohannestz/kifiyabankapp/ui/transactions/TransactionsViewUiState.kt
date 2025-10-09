package com.github.yohannestz.kifiyabankapp.ui.transactions

import androidx.compose.runtime.Immutable
import com.github.yohannestz.kifiyabankapp.data.model.Transaction
import com.github.yohannestz.kifiyabankapp.ui.base.state.UiState

@Immutable
data class TransactionsViewUiState(
    val transactions: List<Transaction> = emptyList(),
    val selectedFilter: TransactionFilter = TransactionFilter.ALL,
    override val isLoading: Boolean = false,
    override val message: String? = null,
    override val messageId: Long? = null
) : UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
    override fun setMessage(value: String?, messageId: Long) = copy(message = value, messageId = messageId)
}

enum class TransactionFilter { ALL, INCOME, EXPENSE }
