package com.github.yohannestz.kifiyabankapp.ui.home

import androidx.compose.runtime.Immutable
import com.github.yohannestz.kifiyabankapp.ui.base.state.UiState

@Immutable
data class HomeViewUiState(
    override val isLoading: Boolean = false,
    override val message: String? = null
): UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
}