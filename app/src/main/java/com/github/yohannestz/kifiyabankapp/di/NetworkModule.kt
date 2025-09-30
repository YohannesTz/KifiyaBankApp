package com.github.yohannestz.kifiyabankapp.di

import com.github.yohannestz.kifiyabankapp.data.remote.network.ktorHttpClient
import org.koin.dsl.module

val networkModule = module {
    single { ktorHttpClient }
}