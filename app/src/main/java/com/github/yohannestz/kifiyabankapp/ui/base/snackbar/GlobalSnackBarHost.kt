package com.github.yohannestz.kifiyabankapp.ui.base.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun GlobalSnackBarHost(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    colors: GlobalSnackBarColors = GlobalSnackBarColors(),
    onEventShown: (() -> Unit)? = null
): SnackbarHostState {
    val scope = rememberCoroutineScope()

    ObserveAsEvents(flow = GlobalSnackBarController.events) { event ->
        scope.launch {
            if (event.withDismissPrevious) snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.label,
                duration = event.duration
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.onAction?.invoke()
            }

            onEventShown?.invoke()
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier.padding(16.dp),
    ) { data ->
        val bgColor = when (data.visuals.messageType(colors)) {
            SnackbarType.Success -> colors.successBackground
            SnackbarType.Warning -> colors.warningBackground
            SnackbarType.Error -> colors.errorBackground
            else -> colors.infoBackground
        }

        Snackbar(
            snackbarData = data,
            containerColor = bgColor,
            contentColor = Color.White,
            actionColor = Color.White
        )
    }

    return snackbarHostState
}

private fun SnackbarVisuals.messageType(colors: GlobalSnackBarColors): SnackbarType {
    return SnackbarType.Info
}

data class GlobalSnackBarColors(
    val infoBackground: Color = Color(0xFF323232),
    val successBackground: Color = Color(0xFF2E7D32),
    val warningBackground: Color = Color(0xFFF9A825),
    val errorBackground: Color = Color(0xFFC62828),
)
