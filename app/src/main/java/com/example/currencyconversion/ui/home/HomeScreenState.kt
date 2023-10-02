package com.example.currencyconversion.ui.home

import com.example.currencyconversion.data.model.ExchangeData
import com.example.currencyconversion.data.model.ExchangeResult

/**
 * UIState Data Class for HomeScreen
 */
data class HomeScreenState(
    val currencyList: List<ExchangeData> = emptyList(),
    val resultantValue: List<ExchangeResult> = emptyList(),
    val inputtedAmount: String = "0",
    val selectedExchangeCode: String = "USD",
    val isExpanded: Boolean = false
)
