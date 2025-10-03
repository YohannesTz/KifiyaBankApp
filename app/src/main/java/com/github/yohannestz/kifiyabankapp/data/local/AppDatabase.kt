package com.github.yohannestz.kifiyabankapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.yohannestz.kifiyabankapp.data.local.dao.AccountDao
import com.github.yohannestz.kifiyabankapp.data.local.dao.TransactionDao
import com.github.yohannestz.kifiyabankapp.data.local.dao.UserDao
import com.github.yohannestz.kifiyabankapp.data.local.entities.AccountEntity
import com.github.yohannestz.kifiyabankapp.data.local.entities.TransactionEntity
import com.github.yohannestz.kifiyabankapp.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        AccountEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}