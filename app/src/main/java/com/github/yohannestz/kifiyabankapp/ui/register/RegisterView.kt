package com.github.yohannestz.kifiyabankapp.ui.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager

@Composable
fun RegisterView(
    navActionManager: NavActionManager
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Register View")
    }
}