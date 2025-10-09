package com.github.yohannestz.kifiyabankapp.ui.cards

import androidx.compose.runtime.Immutable
import com.github.yohannestz.kifiyabankapp.data.dto.billpayment.BillPaymentResponse
import com.github.yohannestz.kifiyabankapp.data.model.BankAccount
import com.github.yohannestz.kifiyabankapp.ui.base.state.UiState

@Immutable
data class CardsViewUiState(
    val bills: List<BillPaymentResponse> = emptyList(),
    val accounts: List<BankAccount> = emptyList(),
    val isLoadingBills: Boolean = false,
    override val isLoading: Boolean = false,
    override val message: String? = null,
    override val messageId: Long? = null
): UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
    override fun setMessage(value: String?, messageId: Long) = copy(message = value, messageId = messageId)
}