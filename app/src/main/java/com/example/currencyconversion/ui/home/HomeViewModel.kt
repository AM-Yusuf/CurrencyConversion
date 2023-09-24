package com.example.currencyconversion.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconversion.data.remote.Resource
import com.example.currencyconversion.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
): ViewModel() {

    init {
        getCurrencyList()
    }


    fun getCurrencyList() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val res = currencyRepository.getCurrencyList()
                when(res.status) {
                    Resource.Status.SUCCESS -> {
                        val rates = res.data
                        //Log.d("API","Api result: ${rates?.base.toString()}")
                        //println("Print Api result: ${rates?.toString()}")
                    }
                    Resource.Status.LOADING -> {
                        Log.d("API","Api loading")
                    }
                    Resource.Status.ERROR -> {
                        Log.d("API","${res.message}")
                    }
                    else -> {
                        Log.d("API","${res.message}")
                    }
                }
            }
        }
    }
}