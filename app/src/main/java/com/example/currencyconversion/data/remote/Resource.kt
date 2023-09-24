package com.example.currencyconversion.data.remote

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val printRequest: String = "",
    val message: String?,
    val code: Int? = null
) {

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

    companion object {

        fun <T> success(data: T, printRequest: String): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data, printRequest = printRequest, message = null)
        }

        fun <T> error(message: String, code: Int, data: T? = null, printRequest: String = ""): Resource<T> {
            return Resource(status = Status.ERROR, data = data, printRequest = printRequest, message = message, code = code)
        }

        fun <T> loading(data: T? = null, printRequest: String): Resource<T> {
            return Resource(status = Status.LOADING, data = data, printRequest = printRequest, message = null )
        }

        fun <T> completed(data: T? = null, printRequest: String = ""): Resource<T> {
            return Resource(status = Status.COMPLETED, data = data, printRequest = printRequest, message = null)
        }

    }
}
