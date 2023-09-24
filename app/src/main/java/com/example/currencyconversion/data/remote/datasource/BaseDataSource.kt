package com.example.currencyconversion.data.remote.datasource

import com.example.currencyconversion.data.remote.Resource
import retrofit2.Response


abstract class BaseDataSource {

    /**
     * Get result from call and return generic response
     */
    protected open suspend fun <T> getResult( call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body, "emptyPrintRequest")
            }
            return error(" ${response.code()} ${response.message()}", printRequest =  "printRequest", code = response.code())
        } catch (e: Exception) {
            return error(e.message ?: e.toString(), code = 0, printRequest = "")
        }
    }

    private fun <T> error(message: String, code: Int, printRequest: String): Resource<T> {
        return Resource.error(
            message = "Network call has failed for the following reason: $message",
            printRequest = printRequest, code = code
        )
    }

}