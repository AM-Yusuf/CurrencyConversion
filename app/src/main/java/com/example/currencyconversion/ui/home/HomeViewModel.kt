package com.example.currencyconversion.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconversion.data.entities.Rate
import com.example.currencyconversion.data.model.CurrencyCodeName
import com.example.currencyconversion.data.remote.Resource
import com.example.currencyconversion.data.repository.CurrencyRepository
import com.example.currencyconversion.util.LogicUnit
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
): ViewModel() {

    /**
     * Holding homeScreenEvent on the view model. (Stateless ui pattern)
     */
    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeScreenState())



    /**
     * Initialization
     */
    init {
        getCurrencyRates()
    }


    /**
     * Get currency rate(Api call)
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
     * @param event Right homeScreen event.
     */
    fun onHomeScreenEvent(event: HomeScreenEvent) {
        when(event) {
            is OnChangeCurrency -> {
                _homeScreenState.update {
                    it.copy(selectedExchangeCode = event.currencyCode)
                }
                currencyConversion()

            }
            is OnTypeAmountField -> {
                _homeScreenState.update {
                    it.copy(inputtedAmount = event.value)
                }
                currencyConversion()
            }
            is OnExpandPullDown -> {
                _homeScreenState.update {
                    it.copy(isExpanded = event.isExpanded)
                }
            }
        }
    }


    /**
     * Currency conversion logic
     */
    private fun currencyConversion() {
        val inputtedValue = _homeScreenState.value.inputtedAmount
        val validUserInput = if (inputtedValue.isNotEmpty()) inputtedValue.toFloat() else 0f


        val currencyCode = _homeScreenState.value.selectedExchangeCode
        var selectedCurrencyRatePerUSD = 1f
        _homeScreenState.value.currencyList.forEach{ (currencyName, rate) ->
            if (currencyName == currencyCode){
                selectedCurrencyRatePerUSD = rate
            }
        }


        val tempList: MutableList<Rate> = mutableListOf()

        _homeScreenState.value.currencyList.forEach {(currencyCode, currencyRatePerUSD) ->
            val result = LogicUnit.currencyConversion(validUserInput, currencyRatePerUSD, selectedCurrencyRatePerUSD)
            tempList.add(Rate(currencyCode, result))
        }

        _homeScreenState.update {
            it.copy(resultantValue = tempList)
        }
    }
}