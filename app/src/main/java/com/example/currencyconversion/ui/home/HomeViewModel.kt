package com.example.currencyconversion.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconversion.data.model.ExchangeResult
import com.example.currencyconversion.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Home view model.
 * Holding UIState and perform event operation for Home screen.
 * Fetching Data from remote source and display on Home Scree.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
): ViewModel() {

    /**
     * Holding homeScreenState
     */
    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeScreenState())



    /**
     * Initialization(fetching remote data while opening the home screen)
     */
    init {
        getCurrencyRates()
    }


    /**
     * Get currency rate(Api call)
     * Fetch remote data and pass to UIState
     */
    private fun getCurrencyRates() {
        viewModelScope.launch(Dispatchers.IO) {
            val flowRates = currencyRepository.getCurrencyRates()
            flowRates.collectLatest { rates ->
                _homeScreenState.update {
                    it.copy(currencyList = rates)
                }
                currencyConversion()
            }
        }
    }


    /**
     * This API for handling events on the home screen.
     * Get user action as an event and perform the intended action
     * @param event homeScreen event.
     */
    fun onHomeScreenEvent(event: HomeScreenEvent) {
        when(event) {
            is OnChangeCurrency -> {
                // perform operation when user change currency
                _homeScreenState.update {
                    it.copy(selectedExchangeCode = event.currencyCode)
                }
                currencyConversion()
            }
            is OnTypeAmountField -> {
                // perform operation when user input amount
                _homeScreenState.update {
                    it.copy(inputtedAmount = event.value)
                }
                currencyConversion()
            }
            is OnExpandPullDown -> {
                //perform operation when user tap on expand button
                _homeScreenState.update {
                    it.copy(isExpanded = event.isExpanded)
                }
            }
        }
    }


    /**
     * Currency conversion method.
     * Perform the exchange operation based on the selected value.
     */
    private fun currencyConversion() {

        // get user input from the UIState
        val inputtedValue = _homeScreenState.value.inputtedAmount

        // validate user input(When user delete all numbers from the field then the default value is 0 )
        val amount: Double = if (inputtedValue.isNotEmpty()) inputtedValue.toDouble() else 0.0

        // get user selected currency from the UIState
        val targetCurrency = _homeScreenState.value.selectedExchangeCode

        // set the rate of selected currency based on USD.
        var targetToUsdRate = 1.0
        _homeScreenState.value.currencyList.forEach{ (currencyCode, _, rate ) ->
            if (targetCurrency == currencyCode){
                targetToUsdRate = rate.toDouble()
            }
        }

        // Initiate a temp list of exchange rates.
        val tempList: MutableList<ExchangeResult> = mutableListOf()

        // perform the exchange operation and add to temp list
        _homeScreenState.value.currencyList.forEach {(currencyCode, currencyName, sourceToUsdRate) ->
            val result = HomeLogicMethod.convertCurrency(amount, sourceToUsdRate.toDouble(), targetToUsdRate)
            tempList.add(ExchangeResult(currencyCode, currencyName, result))
        }

        // Update the UIState with the exchange results
        _homeScreenState.update {
            it.copy(resultantValue = tempList)
        }
    }
}