package com.github.yohannestz.kifiyabankapp.ui.home

import com.github.yohannestz.kifiyabankapp.data.model.AccountType
import com.github.yohannestz.kifiyabankapp.ui.base.event.UiEvent

interface HomeViewUiEvent : UiEvent {
    fun loadAccounts()
    fun loadTransactions()
    fun showAccountCreationDialog(value: Boolean)
    fun onAccountTypeSelected(value: AccountType)
    fun setDeposit(value: String)
    fun createAccount()
}