package com.github.yohannestz.kifiyabankapp.ui.login

import androidx.lifecycle.viewModelScope
import com.github.yohannestz.kifiyabankapp.data.dto.ApiException
import com.github.yohannestz.kifiyabankapp.data.dto.login.LoginRequest
import com.github.yohannestz.kifiyabankapp.data.local.entities.UserEntity
import com.github.yohannestz.kifiyabankapp.data.repository.auth.AuthRepository
import com.github.yohannestz.kifiyabankapp.data.repository.preferences.PreferenceRepository
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.Route
import com.github.yohannestz.kifiyabankapp.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        mutableUiState.update {
            it.setLoading(true)
        }

        viewModelScope.launch {
            val loginRequest = LoginRequest(
                username = mutableUiState.value.username.current,
                passwordHash = mutableUiState.value.password.current
            )

            authRepository.login(request = loginRequest).fold(
                onSuccess = { loginResponse ->
                    preferenceRepository.setAccessToken(loginResponse.accessToken)
                    preferenceRepository.setRefreshToken(loginResponse.refreshToken)

                    authRepository.saveUser(
                        UserEntity(
                            id = loginResponse.userId,
                            username = loginResponse.username,
                        )
                    )

                    resetState()
                    showMessage(loginResponse.message)
                    setLoading(false)

                    sendNavigationCommand(Route.Tab.Home)
                },
                onFailure = { error ->
                    val message = when (error) {
                        is ApiException -> error.apiError?.message ?: error.message
                        else -> error.message ?: "Unknown error"
                    }

                    showMessage(message)
                    setLoading(false)
                }
            )
        }
    }

    override fun resetState() {
        mutableUiState.update {
            it.copy(
                username = it.username.getUpdatedState(""),
                password = it.password.getUpdatedState("")
            )
        }
    }
}