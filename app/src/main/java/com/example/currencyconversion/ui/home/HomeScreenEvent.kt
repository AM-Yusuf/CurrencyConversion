package com.example.currencyconversion.ui.home

/**
 *  Event definition for home screen
 */
sealed class HomeScreenEvent

data class OnTypeAmountField(
    val value: String
): HomeScreenEvent()

data class OnChangeCurrency(
    val currencyCode: String
): HomeScreenEvent()

data class OnExpandPullDown(
    val isExpanded: Boolean
): HomeScreenEvent()