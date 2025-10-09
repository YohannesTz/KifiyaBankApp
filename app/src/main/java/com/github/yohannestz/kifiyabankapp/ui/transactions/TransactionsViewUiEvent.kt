package com.github.yohannestz.kifiyabankapp.ui.transactions

interface TransactionsViewUiEvent {
    fun loadTransactions()
    fun onMessageDisplayed()
    fun onFilterChanged(filter: TransactionFilter)
}