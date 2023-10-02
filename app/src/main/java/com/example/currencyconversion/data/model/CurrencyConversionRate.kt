package com.example.currencyconversion.data.model

// Data class for mapping api response
data class CurrencyConversionRate(
    val base: String = "",
    val disclaimer: String = "",
    val license: String = "",
    val rates: RateX = RateX(),
    val timestamp: Int = 0
)