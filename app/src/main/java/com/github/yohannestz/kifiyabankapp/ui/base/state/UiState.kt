package com.github.yohannestz.kifiyabankapp.ui.base.state

abstract class UiState {
    abstract val isLoading: Boolean
    abstract val message: String?
    abstract val messageId: Long?

    abstract fun setLoading(value: Boolean): UiState
    abstract fun setMessage(value: String?): UiState
    abstract fun setMessage(value: String?, messageId: Long): UiState
}