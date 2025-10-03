package com.github.yohannestz.kifiyabankapp.ui.main

import androidx.compose.runtime.Immutable
import com.github.yohannestz.kifiyabankapp.data.model.User
import com.github.yohannestz.kifiyabankapp.ui.base.state.UiState

@Immutable
data class MainUiState(
    val currentUserProfile: User? = null,
    override val isLoading: Boolean = true,
    override val message: String? = null,
): UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = isLoading)

    override fun setMessage(value: String?) = copy(message = message)
}