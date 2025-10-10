package com.github.yohannestz.kifiyabankapp.ui.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.ui.cards.composables.AccountTypeDropdownMenu
import com.github.yohannestz.kifiyabankapp.ui.home.HomeViewUiEvent
import com.github.yohannestz.kifiyabankapp.ui.home.HomeViewUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAccountCreationSheet(
    uiState: HomeViewUiState,
    event: HomeViewUiEvent?,
    onDismiss: () -> Unit,
) {
    val accountCreationBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        sheetState = accountCreationBottomSheetState,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AccountTypeDropdownMenu(
                selectedType = uiState.selectedAccountType,
                onTypeSelected = { event?.onAccountTypeSelected(it) }
            )

            TextField(
                value = uiState.deposit.current,
                onValueChange = { event?.setDeposit(it) },
                enabled = !uiState.isLoading,
                label = { Text(stringResource(R.string.deposit)) },
                placeholder = { Text(stringResource(R.string.please_enter_deposit_amount)) },
                isError = uiState.deposit.dirty,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    disabledTextColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = MaterialTheme.colorScheme.background
                ),
                suffix = {
                    Text(
                        text = stringResource(R.string.etb_currency),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                supportingText = {
                    if (uiState.deposit.dirty) {
                        Text(
                            text = stringResource(uiState.deposit.errors.first()),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Button(
                onClick = { event?.createAccount() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = uiState.deposit.valid &&
                        uiState.selectedAccountType != null &&
                        !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = stringResource(R.string.create_account))
                }
            }
        }
    }
}
