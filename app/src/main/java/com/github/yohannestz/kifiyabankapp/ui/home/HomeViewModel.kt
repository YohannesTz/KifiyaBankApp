package com.github.yohannestz.kifiyabankapp.ui.home

import com.github.yohannestz.kifiyabankapp.ui.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel (): BaseViewModel<HomeViewUiState>(), HomeViewUiEvent {
    override val mutableUiState: MutableStateFlow<HomeViewUiState> = MutableStateFlow(HomeViewUiState())
}