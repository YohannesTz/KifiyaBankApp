package com.github.yohannestz.kifiyabankapp.data.repository.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.yohannestz.kifiyabankapp.di.getValue
import com.github.yohannestz.kifiyabankapp.di.setValue
import com.github.yohannestz.kifiyabankapp.ui.base.ThemeStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceRepositoryImpl (
    private val dataStore: DataStore<Preferences>,
): PreferenceRepository {
    override val theme: Flow<ThemeStyle>
        get() = dataStore.getValue(THEME_KEY, ThemeStyle.FOLLOW_SYSTEM.name)
            .map { ThemeStyle.valueOfOrNull(it) ?: ThemeStyle.LIGHT }

    override suspend fun setTheme(theme: String) {
        dataStore.setValue(THEME_KEY, theme)
    }

    override val lastTab: Flow<Int>
        get() = dataStore.getValue(LAST_TAB_KEY, 0)

    override suspend fun setLastTab(tab: Int) {
        dataStore.setValue(LAST_TAB_KEY, tab)
    }

    override val accessToken: Flow<String>
        get() = dataStore.getValue(ACCESS_TOKEN_KEY, "")

    override suspend fun setAccessToken(token: String) {
        dataStore.setValue(ACCESS_TOKEN_KEY, token)
    }

    override val refreshToken: Flow<String>
        get() = dataStore.getValue(REFRESH_TOKEN_KEY, "")

    override suspend fun setRefreshToken(token: String) {
        dataStore.setValue(REFRESH_TOKEN_KEY, token)
    }

    override suspend fun clearPreferences() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        val THEME_KEY = stringPreferencesKey("theme")
        val LAST_TAB_KEY = intPreferencesKey("last_tab")
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }
}