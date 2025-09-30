package com.github.yohannestz.kifiyabankapp.ui.base.event

import android.content.Context

interface UiEvent {
    fun showMessage(throwable: Throwable?)
    fun showMessage(exception: Exception?)
    fun showMessage(message: String?)
    fun showMessage(messageRes: Int, context: Context)
    fun onMessageDisplayed()
}