package com.github.yohannestz.kifiyabankapp.ui.base.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.github.yohannestz.kifiyabankapp.data.model.User

@Composable
fun ProvideCurrentUser(
    user: User?,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalCurrentUser provides user) {
        content()
    }
}