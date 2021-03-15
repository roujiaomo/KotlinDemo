package com.uniqlo.circle.architecture.data.api

import com.example.kotlindemo.http.convertRetrofitExceptionToApiException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FlowCallAdapter<T>(
    private val responseType: Type,
    private val retrofit: Retrofit
) : CallAdapter<T, Flow<T>> {

    override fun adapt(call: Call<T>): Flow<T> {
        return flow {

            emit(suspendCancellableCoroutine { continuation ->

                continuation.invokeOnCancellation {
                    try {
                        call.cancel()
                    } catch (throwable: Throwable) {
                        // Ignore cancel throwable
                    }
                }

                try {
                    val response = call.execute()
                    response.body()?.let {
                        continuation.resume(it)
                    }
                } catch (throwable: Throwable) {
                    if (!continuation.isCancelled) {
                        continuation.resumeWithException(convertRetrofitExceptionToApiException(throwable, retrofit))
                    }
                }
            })
        }
    }

    override fun responseType() = responseType
}