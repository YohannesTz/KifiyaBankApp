package com.github.yohannestz.kifiyabankapp.ui.cards

import androidx.compose.runtime.Immutable
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.data.dto.billpayment.BillPaymentResponse
import com.github.yohannestz.kifiyabankapp.data.model.AccountType
import com.github.yohannestz.kifiyabankapp.data.model.BankAccount
import com.github.yohannestz.kifiyabankapp.ui.base.state.TextEditorState
import com.github.yohannestz.kifiyabankapp.ui.base.state.UiState

@Immutable
data class CardsViewUiState(
    val deposit: TextEditorState<String> = TextEditorState("").apply {
        addCheck(
            { it.toDoubleOrNull()?.let { value -> value >= 10.0 } ?: false },
            R.string.error_deposit_too_low
        )
    },
    val selectedAccountType: AccountType? = null,
    val showAccountCreationDialog: Boolean = false,
    val bills: List<BillPaymentResponse> = listOf(
        BillPaymentResponse(
            message = "Monthly electricity bill",
            amount = 1341.4,
            accountNumber = "10000500156",
            biller = "EEU"
        )
    ),
    val accounts: List<BankAccount> = emptyList(),
    val isLoadingBills: Boolean = false,
    val isAddingAccount: Boolean = false,
    override val isLoading: Boolean = false,
    override val message: String? = null,
    override val messageId: Long? = null
): UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
    override fun setMessage(value: String?, messageId: Long) = copy(message = value, messageId = messageId)
}