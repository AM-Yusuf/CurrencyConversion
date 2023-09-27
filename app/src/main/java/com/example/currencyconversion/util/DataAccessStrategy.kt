package com.example.currencyconversion.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.currencyconversion.data.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart


/**
 * using live data
 */
fun <T,A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> = liveData(Dispatchers.IO) {
    emit(Resource.loading())
    val source = databaseQuery.invoke().map {
        Resource.success(it, "noPrintRequest")
    }
    emitSource(source = source)

    val responseStatus = networkCall.invoke()
    if (responseStatus.status == Resource.Status.SUCCESS) {
        saveCallResult(responseStatus.data!!)
    } else if (responseStatus.status == Resource.Status.ERROR) {
        emit(Resource.error(message = responseStatus.message!!, code = responseStatus.code!!))
        emitSource(source = source)
    }
}





/**
 * using flow
 */

suspend fun <T, A> performGetOperationTest(
    databaseQuery: suspend () -> T,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): Flow<Resource<T>> = flow {

    emit(Resource.loading())

    val source = flow {
        emit(Resource.success(databaseQuery.invoke(), "noPrintRequest"))
    }

    emitAll(source)
    val responseStatus = networkCall.invoke()
    if (responseStatus.status == Resource.Status.SUCCESS) {
        saveCallResult(responseStatus.data!!)
    } else if (responseStatus.status == Resource.Status.ERROR) {
        emit(Resource.error(message = responseStatus.message!!, code = responseStatus.code!!))
        emitAll(source)
    }
}.onStart { emit(Resource.loading()) }
    .catch { emit(Resource.error(message = it.localizedMessage, code = it.hashCode())) }