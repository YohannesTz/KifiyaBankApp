package com.github.yohannestz.kifiyabankapp.data.repository.preferences

import com.github.yohannestz.kifiyabankapp.ui.base.ThemeStyle
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val theme: Flow<ThemeStyle>
    suspend fun setTheme(theme: String)

    val lastTab: Flow<Int>
    suspend fun setLastTab(tab: Int)
}