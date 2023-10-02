package com.example.currencyconversion.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconversion.data.entities.Rate
import com.example.currencyconversion.data.model.CurrencyCodeName
import com.example.currencyconversion.data.remote.Resource
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
import kotlin.reflect.full.memberProperties

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
     * Currency Name list api call
     * Currently not using.
     */
    private fun getCurrencyNameList() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = currencyRepository.getCurrencyNameList()

            when (response.status) {

                Resource.Status.SUCCESS -> {


                    val tempList: MutableList<CurrencyCodeName> = mutableListOf()

                    response.data?.let {
                        val currencyNameList = CurrencyCodeName::class.memberProperties.map { prop ->
                            prop.name to prop.get(it as CurrencyCodeName)
                        }

                        currencyNameList.forEach { (currencyCode, currencyName) ->
                            tempList.add(CurrencyCodeName(currencyCode, currencyName.toString()))
                        }
                    }

                }

                // do other operation depending on the response status

                else -> {
                    Log.d("API", "Api result fail: ${response.message}")
                }
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
        val validUserInput = if (inputtedValue.isNotEmpty()) inputtedValue.toFloat() else 0f

        // get user selected currency from the UIState
        val currencyCode = _homeScreenState.value.selectedExchangeCode

        // set the rate of selected currency based on USD.
        var selectedCurrencyRatePerUSD = 1f
        _homeScreenState.value.currencyList.forEach{ (currencyName, rate) ->
            if (currencyName == currencyCode){
                selectedCurrencyRatePerUSD = rate
            }
        }

        // Initiate a temp list of exchange rates.
        val tempList: MutableList<Rate> = mutableListOf()

        // perform the exchange operation and add to temp list
        _homeScreenState.value.currencyList.forEach {(currencyCode, currencyRatePerUSD) ->
            val result = HomeLogicMethod.currencyConversion(validUserInput, currencyRatePerUSD, selectedCurrencyRatePerUSD)
            tempList.add(Rate(currencyCode, result))
        }

        // Update the UIState with the exchange results
        _homeScreenState.update {
            it.copy(resultantValue = tempList)
        }
    }
}