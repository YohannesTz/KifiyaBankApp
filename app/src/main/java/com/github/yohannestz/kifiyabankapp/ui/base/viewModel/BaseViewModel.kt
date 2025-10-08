package com.github.yohannestz.kifiyabankapp.ui.base.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yohannestz.kifiyabankapp.ui.base.event.UiEvent
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.Route
import com.github.yohannestz.kifiyabankapp.ui.base.state.UiState
import com.github.yohannestz.kifiyabankapp.utils.Extensions.parseErrorMessageException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S: UiState> : ViewModel(), UiEvent {

    protected abstract val mutableUiState: MutableStateFlow<S>
    val uiState: StateFlow<S> by lazy {
        mutableUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = mutableUiState.value
        )
    }

    private val _navigationCommands = Channel<Route>(Channel.BUFFERED)
    val navigationCommands: SharedFlow<Route> =
        _navigationCommands.receiveAsFlow().shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 0
        )

    override fun sendNavigationCommand(route: Route) {
        viewModelScope.launch {
            _navigationCommands.send(route)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun setLoading(value: Boolean) {
        mutableUiState.update { it.setLoading(value) as S }
    }

    @Suppress("UNCHECKED_CAST")
    override fun showMessage(exception: Exception?) {
        Log.e("MESSAGE", exception.toString())
        mutableUiState.update {
            it.setMessage(
                exception?.parseErrorMessageException() ?: GENERIC_ERROR
            ) as S
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun showMessage(messageRes: Int, context: android.content.Context) {
        mutableUiState.update { it.setMessage(context.getString(messageRes)) as S }
    }

    @Suppress("UNCHECKED_CAST")
    override fun showMessage(throwable: Throwable?) {
        Log.e("MESSAGE", throwable.toString())
        mutableUiState.update {
            it.setMessage(
                throwable?.parseErrorMessageException() ?: GENERIC_ERROR
            ) as S
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun showMessage(message: String?) {
        mutableUiState.update { it.setMessage(message) as S }
    }

    @Suppress("UNCHECKED_CAST")
    override fun showMessage(message: String?, messageId: Long) {
        mutableUiState.update { it.setMessage(message, messageId) as S }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onMessageDisplayed() {
        mutableUiState.update { it.setMessage(null) as S }
    }

    companion object {
        private const val GENERIC_ERROR = "Something was wrong"
        const val FLOW_TIMEOUT = 5_000L
    }
}