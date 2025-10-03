package com.github.yohannestz.kifiyabankapp.di

import com.github.yohannestz.kifiyabankapp.data.repository.accounts.AccountsRepository
import com.github.yohannestz.kifiyabankapp.data.repository.accounts.AccountsRepositoryImpl
import com.github.yohannestz.kifiyabankapp.data.repository.auth.AuthRepository
import com.github.yohannestz.kifiyabankapp.data.repository.auth.AuthRepositoryImpl
import com.github.yohannestz.kifiyabankapp.data.repository.preferences.PreferenceRepository
import com.github.yohannestz.kifiyabankapp.data.repository.preferences.PreferenceRepositoryImpl
import com.github.yohannestz.kifiyabankapp.data.repository.transaction.TransactionsRepository
import com.github.yohannestz.kifiyabankapp.data.repository.transaction.TransactionsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<AccountsRepository> { AccountsRepositoryImpl(get()) }
    single<TransactionsRepository> { TransactionsRepositoryImpl(get()) }
    single<PreferenceRepository> { PreferenceRepositoryImpl(get()) }
}
