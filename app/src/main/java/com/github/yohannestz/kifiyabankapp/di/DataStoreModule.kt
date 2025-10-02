package com.github.yohannestz.kifiyabankapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import java.io.File

const val DEFAULT_DATASTORE = "kifiya_bank_app_datastore"

val dataStoreModule = module {
    single {
        provideDataStore(get(), DEFAULT_DATASTORE)
    }
}

private fun provideDataStore(context: Context, name: String) =
    PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(name)
    }

fun <T> DataStore<Preferences>.getValue(key: Preferences.Key<T>) = data.map { it[key] }

fun <T> DataStore<Preferences>.getValue(
    key: Preferences.Key<T>,
    default: T,
) = data.map { it[key] ?: default }

suspend fun <T> DataStore<Preferences>.setValue(
    key: Preferences.Key<T>,
    value: T?
) = edit {
    if (value != null) it[key] = value
    else it.remove(key)
}

fun <T> DataStore<Preferences>.getValueBlocking(key: Preferences.Key<T>) =
    runBlocking { data.first() }[key]

public fun Context.preferencesDataStoreFile(name: String): File =
    this.dataStoreFile("$name.preferences_pb")

public fun Context.dataStoreFile(fileName: String): File =
    File(applicationContext.filesDir, "datastore/$fileName")
