package com.github.yohannestz.kifiyabankapp.ui.base.snackbar

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

enum class SnackbarType {
    Info, Success, Warning, Error
}

data class SnackbarAction(
    val label: String,
    val onAction: suspend () -> Unit
)

data class GlobalSnackBarEvent(
    val message: String,
    val type: SnackbarType = SnackbarType.Info,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val action: SnackbarAction? = null,
    val withDismissPrevious: Boolean = true
)

object GlobalSnackBarController {
    private val _events = Channel<GlobalSnackBarEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    suspend fun show(
        message: String,
        type: SnackbarType = SnackbarType.Info,
        duration: SnackbarDuration = SnackbarDuration.Short,
        action: SnackbarAction? = null,
        dismissPrevious: Boolean = true
    ) {
        _events.send(
            GlobalSnackBarEvent(
                message = message,
                type = type,
                duration = duration,
                action = action,
                withDismissPrevious = dismissPrevious
            )
        )
    }

    // Convenience helpers
    suspend fun info(msg: String, duration: SnackbarDuration = SnackbarDuration.Short) =
        show(msg, SnackbarType.Info, duration)

    suspend fun success(msg: String, duration: SnackbarDuration = SnackbarDuration.Short) =
        show(msg, SnackbarType.Success, duration)

    suspend fun warning(msg: String, duration: SnackbarDuration = SnackbarDuration.Long) =
        show(msg, SnackbarType.Warning, duration)

    suspend fun error(msg: String, duration: SnackbarDuration = SnackbarDuration.Long) =
        show(msg, SnackbarType.Error, duration)
}
