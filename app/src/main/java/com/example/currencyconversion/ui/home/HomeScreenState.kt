package com.example.currencyconversion.ui.home

import com.example.currencyconversion.data.model.CurrencyRates

/**
 * State class for HomeScreen
 */
data class HomeScreenState(
    val currencyList: List<CurrencyRates> = emptyList(),
    val resultantValue: List<CurrencyRates> = emptyList(),
    val inputtedAmount: String = "0.0",
    val selectedExchangeCode: String = "USD",
    val isExpanded: Boolean = false
)
