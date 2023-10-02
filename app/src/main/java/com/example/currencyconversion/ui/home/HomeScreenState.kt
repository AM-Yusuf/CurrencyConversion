package com.example.currencyconversion.ui.home

import com.example.currencyconversion.data.entities.Rate

/**
 * UIState Data Class for HomeScreen
 */
data class HomeScreenState(
    val currencyList: List<Rate> = emptyList(),
    val resultantValue: List<Rate> = emptyList(),
    val inputtedAmount: String = "0.0",
    val selectedExchangeCode: String = "USD",
    val isExpanded: Boolean = false
)
