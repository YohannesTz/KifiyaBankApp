package com.github.yohannestz.kifiyabankapp.ui.cards

import com.github.yohannestz.kifiyabankapp.ui.base.event.UiEvent

interface CardsViewUiEvent : UiEvent {
    fun loadAccounts()
    fun loadBills()
}