package com.example.currencyconversion.ui.home.composeComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.currencyconversion.data.model.ExchangeResult


/**
 * Exchange result view component
 * @param currencyRates provide currency rate to display into cell
 */
@Composable
fun ConversionsListCell(
    currencyRates: ExchangeResult,
    selectedCurrency: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 2.dp)
            .background(if(currencyRates.currencyCode == selectedCurrency) Color.Green.copy(0.2f) else Color.Gray.copy(0.5f)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = currencyRates.currencyName
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = currencyRates.currencyCode
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = currencyRates.currencyRate
        )
    }
}