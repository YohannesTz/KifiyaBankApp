package com.github.yohannestz.kifiyabankapp.ui.register

import com.github.yohannestz.kifiyabankapp.ui.base.event.UiEvent

interface RegisterViewUiEvent: UiEvent {
    fun setFirstNameTouched()
    fun setFirstNameEdited()
    fun setFirstName(value: String)

    fun setLastNameTouched()
    fun setLastNameEdited()
    fun setLastName(value: String)

    fun setUserNameTouched()
    fun setUserNameEdited()
    fun setUserName(value: String)

    fun setPhoneNumberTouched()
    fun setPhoneNumberEdited()
    fun setPhoneNumber(value: String)

    fun setPasswordTouched()
    fun setPasswordEdited()
    fun setPassword(value: String)

    fun register()

    fun resetState()
}