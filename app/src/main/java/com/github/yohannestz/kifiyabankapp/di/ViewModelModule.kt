package com.github.yohannestz.kifiyabankapp.di

import com.github.yohannestz.kifiyabankapp.ui.cards.CardsViewModel
import com.github.yohannestz.kifiyabankapp.ui.home.HomeViewModel
import com.github.yohannestz.kifiyabankapp.ui.login.LoginViewModel
import com.github.yohannestz.kifiyabankapp.ui.main.MainViewModel
import com.github.yohannestz.kifiyabankapp.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CardsViewModel)
}