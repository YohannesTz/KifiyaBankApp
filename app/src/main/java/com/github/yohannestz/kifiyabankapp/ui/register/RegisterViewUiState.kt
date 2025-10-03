package com.github.yohannestz.kifiyabankapp.ui.register

import androidx.compose.runtime.Immutable
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.ui.base.state.TextEditorState
import com.github.yohannestz.kifiyabankapp.ui.base.state.UiState

@Immutable
data class RegisterViewUiState(
    val firstName: TextEditorState<String> = TextEditorState("").apply {
        addCheck({ it.isNotEmpty() }, R.string.error_firstname_cannot_be_empty)
    },
    val lastName: TextEditorState<String> = TextEditorState("").apply {
        addCheck({ it.isNotEmpty() }, R.string.error_lastName_cannot_be_empty)
    },
    val userName: TextEditorState<String> = TextEditorState("").apply {
        addCheck({ it.isNotEmpty() }, R.string.error_userName_cannot_be_empty)
    },
    val phoneNumber: TextEditorState<String> = TextEditorState("").apply {
        addCheck({ it.isNotEmpty() }, R.string.error_phoneNumber_cannot_be_empty)
    },
    val password: TextEditorState<String> = TextEditorState("").apply {
        addCheck({ it.isNotEmpty() }, R.string.error_password_cannot_be_empty)
    },
    override val isLoading: Boolean = false,
    override val message: String? = null
): UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
}