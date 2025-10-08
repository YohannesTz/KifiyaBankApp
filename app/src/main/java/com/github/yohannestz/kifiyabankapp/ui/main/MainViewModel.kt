package com.github.yohannestz.kifiyabankapp.ui.main

import androidx.lifecycle.viewModelScope
import com.github.yohannestz.kifiyabankapp.data.repository.auth.AuthRepository
import com.github.yohannestz.kifiyabankapp.data.repository.preferences.PreferenceRepository
import com.github.yohannestz.kifiyabankapp.ui.base.ThemeStyle
import com.github.yohannestz.kifiyabankapp.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val authRepository: AuthRepository
): BaseViewModel<MainUiState>() {
    override val mutableUiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())

    val lastTab = preferenceRepository.lastTab

    val hasAnAccount: StateFlow<Boolean> = preferenceRepository.accessToken
        .map { it.isNotEmpty() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun saveLastTab(value: Int) = viewModelScope.launch {
        preferenceRepository.setLastTab(value)
    }

    val theme = preferenceRepository.theme
        .stateIn(viewModelScope, SharingStarted.Eagerly, ThemeStyle.FOLLOW_SYSTEM)

    init {
        viewModelScope.launch {
            authRepository.getCurrentUserFlow().collectLatest {
                mutableUiState.update { state ->
                    state.copy(
                        currentUserProfile = it?.toUser(),
                        isLoading = false
                    )
                }
            }
        }
    }
}