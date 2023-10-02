package com.example.currencyconversion.data.remote

/**
 * Generic output for api response
 */
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val printRequest: String = "",
    val message: String?,
    val code: Int? = null
) {

    // Define the cases
    enum class Status {
        /** HTTP 200 Success */
        SUCCESS,
        /** Server, communication error etc */
        ERROR,
        /** Currently loading */
        LOADING,
        /** request completed */
        COMPLETED
    }

    // Response method definition
    companion object {

        // in case of success
        fun <T> success(data: T, printRequest: String): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data, printRequest = printRequest, message = null)
        }

        // in case of erro
        fun <T> error(message: String, code: Int, data: T? = null, printRequest: String = ""): Resource<T> {
            return Resource(status = Status.ERROR, data = data, printRequest = printRequest, message = message, code = code)
        }

        // in case of loading data
        fun <T> loading(data: T? = null, printRequest: String = ""): Resource<T> {
            return Resource(status = Status.LOADING, data = data, printRequest = printRequest, message = null )
        }

        // in case of completion
        fun <T> completed(data: T? = null, printRequest: String = ""): Resource<T> {
            return Resource(status = Status.COMPLETED, data = data, printRequest = printRequest, message = null)
        }
    }
}
