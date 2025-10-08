package com.github.yohannestz.kifiyabankapp.ui.register

import androidx.lifecycle.viewModelScope
import com.github.yohannestz.kifiyabankapp.data.dto.ApiException
import com.github.yohannestz.kifiyabankapp.data.dto.register.RegisterRequest
import com.github.yohannestz.kifiyabankapp.data.model.CountryCode
import com.github.yohannestz.kifiyabankapp.data.repository.auth.AuthRepository
import com.github.yohannestz.kifiyabankapp.data.repository.preferences.PreferenceRepository
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.Route
import com.github.yohannestz.kifiyabankapp.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val authRepository: AuthRepository
): BaseViewModel<RegisterViewUiState>(), RegisterViewUiEvent {
    override val mutableUiState: MutableStateFlow<RegisterViewUiState> = MutableStateFlow(RegisterViewUiState())

    override fun setFirstNameTouched() {
       mutableUiState.update {
           it.copy(
              firstName = it.firstName.getTouchedState()
           )
       }
    }

    override fun setFirstNameEdited() {
       mutableUiState.update {
           it.copy(
              firstName = it.firstName.getEditedState()
           )
       }
    }

    override fun setFirstName(value: String) {
       mutableUiState.update {
           it.copy(
              firstName = it.firstName.getUpdatedState(value)
           )
       }
    }

    override fun setLastNameTouched() {
       mutableUiState.update {
           it.copy(
              lastName = it.lastName.getTouchedState()
           )
       }
    }

    override fun setLastNameEdited() {
       mutableUiState.update {
           it.copy(
              lastName = it.lastName.getEditedState()
           )
       }
    }

    override fun setLastName(value: String) {
       mutableUiState.update {
           it.copy(
              lastName = it.lastName.getUpdatedState(value)
           )
       }
    }

    override fun setUserNameTouched() {
       mutableUiState.update {
           it.copy(
              userName = it.userName.getTouchedState()
           )
       }
    }

    override fun setUserNameEdited() {
       mutableUiState.update {
           it.copy(
              userName = it.userName.getEditedState()
           )
       }
    }

    override fun setUserName(value: String) {
       mutableUiState.update {
           it.copy(
              userName = it.userName.getUpdatedState(value)
           )
       }
    }

    override fun setPhoneNumberTouched() {
       mutableUiState.update {
           it.copy(
              phoneNumber = it.phoneNumber.getTouchedState()
           )
       }
    }

    override fun setPhoneNumberEdited() {
       mutableUiState.update {
           it.copy(
              phoneNumber = it.phoneNumber.getEditedState()
           )
       }
    }

    override fun setPhoneNumber(value: String) {
       mutableUiState.update {
           it.copy(
              phoneNumber = it.phoneNumber.getUpdatedState(value)
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

    override fun register() {
        setLoading(true)

        viewModelScope.launch(Dispatchers.IO) {
            val registerRequest = RegisterRequest(
                firstName = mutableUiState.value.firstName.current,
                lastName = mutableUiState.value.lastName.current,
                username = mutableUiState.value.userName.current,
                phoneNumber = "${CountryCode.ET.code}${mutableUiState.value.phoneNumber.current}",
                passwordHash = mutableUiState.value.password.current
            )

            authRepository.register(request = registerRequest).fold(
                onSuccess = { registerResponse ->
                    showMessage(registerResponse.message)
                    setLoading(false)

                    sendNavigationCommand(Route.Login)
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

    }
}