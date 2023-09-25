package com.example.currencyconversion.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconversion.data.entities.RateX
import com.example.currencyconversion.data.model.CurrencyCodeName
import com.example.currencyconversion.data.model.CurrencyRates
import com.example.currencyconversion.data.remote.Resource
import com.example.currencyconversion.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
        getCurrencyList()
    }


    /**
     * Get currency rate(Api call)
     */
    private fun getCurrencyList() {

        viewModelScope.launch(Dispatchers.IO) {

            val rateList = currencyRepository.getCurrencyRate()

            rateList?.let {

                val tempList: MutableList<CurrencyRates> = mutableListOf()

                // Use Kotlin reflection to get all properties of the Currency class

                val currencyList = RateX::class.memberProperties.map { prop ->
                    prop.name to prop.get(it).toString().toFloat()
                }

                currencyList.forEach { (currencyName, rate) ->
                   tempList.add(CurrencyRates(currencyName, rate))
                }

                _homeScreenState.update {
                    it.copy(currencyList = tempList)
                }
            }
            currencyConversion()
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
        val userInput = if (inputtedValue.isNotEmpty()) inputtedValue.toFloat() else 0f


        val currencyCode = _homeScreenState.value.selectedExchangeCode
        var baseCurrencyRate = 1f
        _homeScreenState.value.currencyList.forEach{ (currencyName, rate) ->
            if (currencyName == currencyCode){
                baseCurrencyRate = rate
            }
        }


        val tempList: MutableList<CurrencyRates> = mutableListOf()

        _homeScreenState.value.currencyList.forEach {(currencyName, rate) ->
            val result = ( rate / baseCurrencyRate) * userInput
            tempList.add(CurrencyRates(currencyName, result))
        }

        _homeScreenState.update {
            it.copy(resultantValue = tempList)
        }
    }
}