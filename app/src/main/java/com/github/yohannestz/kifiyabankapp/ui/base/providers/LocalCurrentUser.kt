package com.github.yohannestz.kifiyabankapp.ui.base.providers

import androidx.compose.runtime.compositionLocalOf
import com.github.yohannestz.kifiyabankapp.data.model.User

val LocalCurrentUser = compositionLocalOf<User?> {
    error("No user profile found! Did you forget to add a UserProfileProvider?")
}