package com.github.yohannestz.kifiyabankapp.ui.cards.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.yohannestz.kifiyabankapp.R
import com.github.yohannestz.kifiyabankapp.data.model.AccountType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTypeDropdownMenu(
    selectedType: AccountType?,
    onTypeSelected: (AccountType) -> Unit,
) {
    val expanded = remember { mutableStateOf(false) }
    val types = AccountType.entries

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedType?.titleResId?.let { stringResource(it) }.orEmpty(),
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.account_type)) },
            placeholder = { Text(stringResource(R.string.please_select_account_type)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            containerColor = MaterialTheme.colorScheme.background
        ) {
            types.forEach { type ->
                DropdownMenuItem(
                    text = { Text(stringResource(type.titleResId)) },
                    onClick = {
                        onTypeSelected(type)
                        expanded.value = false
                    }
                )
            }
        }
    }
}