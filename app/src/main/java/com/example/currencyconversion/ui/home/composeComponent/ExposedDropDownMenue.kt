package com.example.currencyconversion.ui.home.composeComponent

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconversion.ui.home.homeStateEvent.DropDownState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropDownMenu(
    state: DropDownState,
    conversionUnit: List<String>,
    event: () -> Unit
) {

    ExposedDropdownMenuBox(
        expanded = state.isExpanded,
        onExpandedChange = {  }
    ) {

        TextField(
            value = "",
            onValueChange = {},
            trailingIcon = { },
            placeholder = { Text(text = "Select Unit") },
            modifier = Modifier.menuAnchor(),
            readOnly = true
        )

        ExposedDropdownMenu(
            expanded = true,
            onDismissRequest = {  }
        ) {
            conversionUnit.forEach {unit ->
                DropdownMenuItem(
                    text = { Text(text = unit) },
                    onClick = {
                    }
                )
            }
        }
    }
}