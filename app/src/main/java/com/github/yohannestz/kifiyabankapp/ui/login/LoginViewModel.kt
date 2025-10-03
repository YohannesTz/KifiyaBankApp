package com.github.yohannestz.kifiyabankapp.ui.login

import com.github.yohannestz.kifiyabankapp.data.repository.auth.AuthRepository
import com.github.yohannestz.kifiyabankapp.data.repository.preferences.PreferenceRepository
import com.github.yohannestz.kifiyabankapp.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val authRepository: AuthRepository,
): BaseViewModel<LoginViewUiState>(), LoginViewUiEvent {
    override val mutableUiState: MutableStateFlow<LoginViewUiState> = MutableStateFlow(LoginViewUiState())

    override fun setUsernameTouched() {
        mutableUiState.update {
            it.copy(
                username = it.username.getTouchedState()
            )
        }
    }

    override fun setUsernameEdited() {
        mutableUiState.update {
            it.copy(
                username = it.username.getEditedState()
            )
        }
    }

    override fun setUsername(value: String) {
        mutableUiState.update {
            it.copy(
                username = it.username.getUpdatedState(value)
            )
        }
    }

    override fun setPasswordTouched() {
        mutableUiState.update {
            it.copy(
                password = it.password.getTouchedState()
            )
        }
    }

    override fun setPasswordEdited() {
        mutableUiState.update {
            it.copy(
                password = it.password.getEditedState()
            )
        }
    }

    override fun setPassword(value: String) {
        mutableUiState.update {
            it.copy(
                password = it.password.getUpdatedState(value)
            )
        }
    }

    override fun login() {

    }

    override fun resetState() {

    }
}