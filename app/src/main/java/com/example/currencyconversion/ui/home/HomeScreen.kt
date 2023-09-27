package com.example.currencyconversion.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.currencyconversion.ui.home.composeComponent.ConversionsListCell

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {

    val state = viewModel.homeScreenState.collectAsState().value
    val event: (HomeScreenEvent) -> Unit = { viewModel.onHomeScreenEvent(it) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        var isExpanded by remember { mutableStateOf(false) }

        /**User input field**/
        TextField(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            value = state.inputtedAmount.toString(),
            onValueChange = {
                event(OnTypeAmountField(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
        )


        /**DropDown menu for Currency selection**/
        ExposedDropdownMenuBox(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 10.dp),
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {


            TextField(
                value = state.selectedExchangeCode ,
                onValueChange = { event(OnChangeCurrency(it)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)},
                placeholder = { Text(text = "Select Unit") },
                modifier = Modifier.menuAnchor(),
                readOnly = true
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {

                state.currencyList.forEach { currencyRate ->
                    DropdownMenuItem(
                        text = { Text(text = currencyRate.currencyName) },
                        onClick = {
                            event(OnChangeCurrency(currencyRate.currencyName))
                            isExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        /** Conversion result list view **/
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.resultantValue.isNotEmpty()) {
                LazyColumn {

                    items(state.resultantValue.size) {
                        ConversionsListCell(state.resultantValue[it])
                    }

                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}