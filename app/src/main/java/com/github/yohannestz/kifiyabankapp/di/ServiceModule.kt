package com.github.yohannestz.kifiyabankapp.di

import com.github.yohannestz.kifiyabankapp.data.remote.api.AccountsApiService
import com.github.yohannestz.kifiyabankapp.data.remote.api.AuthApiService
import com.github.yohannestz.kifiyabankapp.data.remote.api.TransactionsApiService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serviceModule = module {
    singleOf(::AuthApiService)
    singleOf(::AccountsApiService)
    singleOf(::TransactionsApiService)
}