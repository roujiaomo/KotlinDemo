package com.example.kotlindemo.http

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 *
 * @author at-hoavo.
 */
class RxCallAdapterWrapper<R>(type: Type, retrofit: Retrofit, wrapped: CallAdapter<R, *>?) :
    BaseRxCallAdapterWrapper<R>(type, retrofit, wrapped) {

    override fun convertRetrofitExceptionToCustomException(throwable: Throwable, retrofit: Retrofit): Throwable {
        return convertRetrofitExceptionToApiException(throwable, retrofit)
    }

    override fun createExceptionForSuccessResponse(response: Any?): Throwable? =
        super.createExceptionForSuccessResponse(response)
}
