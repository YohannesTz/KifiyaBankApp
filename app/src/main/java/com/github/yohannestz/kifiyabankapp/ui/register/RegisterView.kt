package com.github.yohannestz.kifiyabankapp.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.NavActionManager
import com.github.yohannestz.kifiyabankapp.ui.base.navigation.Route
import com.github.yohannestz.kifiyabankapp.ui.base.snackbar.GlobalSnackBarController
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterView(
    navActionManager: NavActionManager
) {
    val viewModel: RegisterViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.navigationCommands.collect { route ->
            navActionManager.navigateTo(route)
        }
    }

    RegisterViewContent(
        uiState = uiState,
        event = viewModel,
        navActionManager = navActionManager
    )
}

@Composable
private fun RegisterViewContent(
    uiState: RegisterViewUiState,
    event: RegisterViewUiEvent?,
    navActionManager: NavActionManager
) {
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            GlobalSnackBarController.info(message)
            event?.onMessageDisplayed()
        }
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, top = 48.dp)
            ) {
                Text(
                    text = stringResource(R.string.welcome),
                    style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.register),
                    style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 180.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp)
                .imePadding(),
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.firstName.current,
                onValueChange = { event?.setFirstName(it) },
                label = { Text(stringResource(R.string.first_name)) },
                placeholder = { Text(stringResource(R.string.please_enter_your_firstname)) },
                isError = uiState.firstName.dirty,
                singleLine = true,
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    if (uiState.firstName.dirty) {
                        Text(
                            text = stringResource(uiState.firstName.errors.first()),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.lastName.current,
                onValueChange = { event?.setLastName(it) },
                label = { Text(stringResource(R.string.last_name)) },
                placeholder = { Text(stringResource(R.string.please_enter_your_lastname)) },
                isError = uiState.lastName.dirty,
                singleLine = true,
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    if (uiState.lastName.dirty) {
                        Text(
                            text = stringResource(uiState.lastName.errors.first()),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.userName.current,
                onValueChange = { event?.setUserName(it) },
                label = { Text(stringResource(R.string.username)) },
                placeholder = { Text(stringResource(R.string.please_enter_your_username)) },
                isError = uiState.userName.dirty,
                singleLine = true,
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    if (uiState.userName.dirty) {
                        Text(
                            text = stringResource(uiState.userName.errors.first()),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.phoneNumber.current,
                onValueChange = {
                    if (it.length <= 10) event?.setPhoneNumber(it)
                },
                label = { Text(stringResource(R.string.phone_number)) },
                placeholder = { Text(stringResource(R.string.please_enter_your_phonenumber)) },
                isError = uiState.phoneNumber.dirty,
                singleLine = true,
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    if (uiState.phoneNumber.dirty) {
                        Text(
                            text = stringResource(uiState.phoneNumber.errors.first()),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                prefix = {
                    Text(text = "+251")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password.current,
                onValueChange = { event?.setPassword(it) },
                label = { Text(stringResource(R.string.password)) },
                placeholder = { Text(stringResource(R.string.please_enter_your_password)) },
                isError = uiState.password.dirty,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (passwordVisible) KeyboardType.Text else KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = textFieldColors(),
                supportingText = {
                    if (uiState.password.dirty) {
                        Text(
                            text = stringResource(uiState.password.errors.first()),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { event?.register() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = uiState.firstName.valid &&
                        uiState.lastName.valid &&
                        uiState.userName.valid &&
                        uiState.phoneNumber.valid &&
                        uiState.password.valid &&
                        !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = stringResource(R.string.register))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(stringResource(R.string.already_have_an_account), color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.width(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = { navActionManager.navigateTo(route = Route.Login, launchSingleTop = true) }) {
                    Text(stringResource(R.string.login), color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
private fun textFieldColors() = TextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.onBackground,
    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
    focusedContainerColor = MaterialTheme.colorScheme.background,
    unfocusedContainerColor = MaterialTheme.colorScheme.background,
)
