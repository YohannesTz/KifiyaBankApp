package com.github.yohannestz.kifiyabankapp.di

import com.github.yohannestz.kifiyabankapp.data.remote.api.accounts.AccountsApiService
import com.github.yohannestz.kifiyabankapp.data.remote.api.accounts.AccountsApiServiceImpl
import com.github.yohannestz.kifiyabankapp.data.remote.api.auth.AuthApiService
import com.github.yohannestz.kifiyabankapp.data.remote.api.auth.AuthApiServiceImpl
import com.github.yohannestz.kifiyabankapp.data.remote.api.transactions.TransactionsApiService
import com.github.yohannestz.kifiyabankapp.data.remote.api.transactions.TransactionsApiServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    single<AuthApiService> { AuthApiServiceImpl(get()) }
    single<AccountsApiService> { AccountsApiServiceImpl(get()) }
    single<TransactionsApiService> { TransactionsApiServiceImpl(get()) }
}
