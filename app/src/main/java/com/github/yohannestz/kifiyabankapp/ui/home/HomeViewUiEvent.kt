package com.github.yohannestz.kifiyabankapp.ui.home

import com.github.yohannestz.kifiyabankapp.ui.base.event.UiEvent

interface HomeViewUiEvent : UiEvent {
    fun loadAccounts()

    fun loadTransactions()
}