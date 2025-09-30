package com.github.yohannestz.kifiyabankapp.ui.base.state

abstract class UiState {
    abstract val isLoading: Boolean
    abstract val message: String?

    abstract fun setLoading(value: Boolean): UiState
    abstract fun setMessage(value: String?): UiState
}