package com.example.currencyconversion.data.remote

import com.example.currencyconversion.util.API_VERSION
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

// Interceptor class for control api call
class OkHttpInterceptor: Interceptor {
    @Throws(IOException::class)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()

        if (originalRequest.url.encodedPath.contains("oauth")) {
            // when need to get an Auth token
            newRequest.url(originalRequest.url.toString().replace(oldValue = "/api/v$API_VERSION", newValue = ""))
        } else {
            if (originalRequest.header("Content-Type") == null) {
                newRequest.header("Content-Type", "application/json")
            }
        }

        return chain.proceed(newRequest.build())
    }
}