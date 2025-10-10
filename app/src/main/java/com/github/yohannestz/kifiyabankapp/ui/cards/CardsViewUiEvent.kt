package com.github.yohannestz.kifiyabankapp.ui.cards

import com.github.yohannestz.kifiyabankapp.data.model.AccountType
import com.github.yohannestz.kifiyabankapp.ui.base.event.UiEvent

interface CardsViewUiEvent : UiEvent {
    fun loadAccounts()
    fun loadBills()
    fun showAccountCreationDialog(value: Boolean)
    fun onAccountTypeSelected(value: AccountType)
    fun setDeposit(value: String)
    fun createAccount()
}