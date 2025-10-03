package com.github.yohannestz.kifiyabankapp.di

import androidx.room.Room
import com.github.yohannestz.kifiyabankapp.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().accountDao() }
    single { get<AppDatabase>().transactionDao() }
}