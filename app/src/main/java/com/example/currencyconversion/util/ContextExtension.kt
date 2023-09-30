package com.example.currencyconversion.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Network connection check
 * @return if connected then true, else false。
 */
fun Context.isNetworkConnected() =
    (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).let {
        val capabilities = it.getNetworkCapabilities(it.activeNetwork)
        capabilities != null
    }