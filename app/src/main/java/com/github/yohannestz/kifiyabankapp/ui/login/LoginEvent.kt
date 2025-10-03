package com.github.yohannestz.kifiyabankapp.ui.login

import com.github.yohannestz.kifiyabankapp.ui.base.event.UiEvent

interface LoginViewUiEvent: UiEvent {
    fun setUsernameTouched()
    fun setUsernameEdited()
    fun setUsername(value: String)
    fun setPasswordTouched()
    fun setPasswordEdited()
    fun setPassword(value: String)
    fun login()
    fun resetState()
}