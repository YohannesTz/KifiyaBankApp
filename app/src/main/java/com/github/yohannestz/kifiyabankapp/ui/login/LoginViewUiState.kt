package com.github.yohannestz.kifiyabankapp.ui.login

import androidx.compose.runtime.Immutable
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.ui.base.state.TextEditorState
import com.github.yohannestz.kifiyabankapp.ui.base.state.UiState

@Immutable
data class LoginViewUiState(
    val username: TextEditorState<String> = TextEditorState("").apply {
        addCheck({ it.isNotEmpty() }, R.string.error_username_cannot_be_empty)
    },
    val password: TextEditorState<String> = TextEditorState("").apply {
        addCheck({ it.isNotEmpty() }, R.string.error_password_cannot_be_empty)
    },
    override val isLoading: Boolean = false,
    override val message: String? = null,
    override val messageId: Long? = null
): UiState() {
    override fun setLoading(value: Boolean) = copy(isLoading = value)
    override fun setMessage(value: String?) = copy(message = value)
    override fun setMessage(value: String?, messageId: Long) = copy(message = value, messageId = messageId)
}