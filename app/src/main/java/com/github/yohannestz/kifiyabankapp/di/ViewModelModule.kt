package com.github.yohannestz.kifiyabankapp.di

import com.github.yohannestz.kifiyabankapp.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
}