package com.example.currencyconversion.ui.home

import android.text.style.UnderlineSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        var userInputAmount by remember { mutableStateOf("Hello") }
        var isExpanded by remember { mutableStateOf(false) }
        val conversionUnit = arrayOf("USD", "JPY", "BDT", "EU")
        var selectedUnit by remember { mutableStateOf(conversionUnit[0]) }
        
        TextField(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            value = userInputAmount,
            onValueChange = { userInputAmount = it}
        )

        ExposedDropdownMenuBox(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 10.dp),
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {

            TextField(
                value = selectedUnit,
                onValueChange = {selectedUnit = it},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)},
                placeholder = { Text(text = "Select Unit") },
                modifier = Modifier.menuAnchor(),
                readOnly = true
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                conversionUnit.forEach {unit ->
                    DropdownMenuItem(
                        text = { Text(text = unit) },
                        onClick = {
                            selectedUnit = unit
                            isExpanded = false
                        }
                    )
                }
            }
        }

        LazyColumn {

            items(conversionUnit.size) {

                ConversionsListCell(
                    title = conversionUnit[it],
                    value = 200.05f
                )

            }
        }
    }
}

@Composable
fun ConversionsListCell(
    title: String,
    value: Float
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
            .background(Color.Gray.copy(0.5f)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = title
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = value.toString()
        )
    }
}