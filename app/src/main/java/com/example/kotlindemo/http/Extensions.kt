package com.example.kotlindemo.http

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun convertRetrofitExceptionToApiException(throwable: Throwable, retrofit: Retrofit): Throwable {
    return when (throwable) {
        is UnknownHostException, is ConnectException -> {
            val apiException = ApiException("", mutableListOf())
            apiException.statusCode = HttpStatus.NETWORK_ERROR_CODE

            apiException
        }

        is SocketTimeoutException -> {
            val apiException = ApiException("", mutableListOf())
            apiException.statusCode = HttpStatus.HTTP_CLIENT_TIMEOUT

            apiException
        }

        is HttpException -> {
            handleRetrofitHttpException(throwable, retrofit) ?: ApiException("", mutableListOf())
        }

        else -> ApiException()
    }
}

private fun handleRetrofitHttpException(throwable: HttpException, retrofit: Retrofit): Throwable? {
    val converter: Converter<ResponseBody, ApiException> =
        retrofit.responseBodyConverter(
            ApiException::class.java,
            arrayOfNulls<Annotation>(0)
        )

    val response = throwable.response()
    val requestUrl = response?.raw()?.request?.url.toString()
    val method = response?.raw()?.request?.method.toString()

    when (response?.code()) {

        HttpStatus.HTTP_BAD_REQUEST -> response.errorBody()?.let {
            return converter.convert(it)?.apply {
                statusCode = HttpStatus.HTTP_BAD_REQUEST
            }
        }

        HttpStatus.HTTP_INTERNAL_ERROR -> response.errorBody()?.let {
            return converter.convert(it)
        }

        HttpStatus.HTTP_FORBIDDEN -> response.errorBody()?.let {
            return converter.convert(it)?.apply {
                statusCode = HttpStatus.HTTP_FORBIDDEN
            }
        }

        HttpStatus.HTTP_NOT_FOUND -> response.errorBody()?.let {
            return converter.convert(it)?.apply {
                statusCode = HttpStatus.HTTP_NOT_FOUND
            }
        }

        HttpStatus.HTTP_NOT_ACCEPTABLE -> response.errorBody()?.let {
            return converter.convert(it)
        }

        HttpStatus.HTTP_CONFLICT -> response.errorBody()?.let {
            return converter.convert(it)?.apply {
                statusCode = HttpStatus.HTTP_CONFLICT
            }
        }

        HttpStatus.HTTP_UNAUTHORIZED -> response.errorBody()?.let { responseBody ->
            val apiException = converter.convert(responseBody)?.apply {
                statusCode = HttpStatus.HTTP_UNAUTHORIZED
            }

            if (!ApiClient.requestUrlNotShowUnAuthorizedError.contains(requestUrl) &&
                !ApiClient.requestUrlNotShowUnAuthorizedAndForceUpdateErrorWithMethod.any {
                    it.key == requestUrl && it.value == method
                }
            ) {

                apiException?.let {
                    RxBus.publish(UnAuthorizeEvent(it))
                }
            }

            return apiException
        }

        HttpStatus.FORCE_UPDATE_ERROR_CODE -> response.errorBody()?.let { responseBody ->
            val apiException = converter.convert(responseBody)?.apply {
                statusCode = HttpStatus.FORCE_UPDATE_ERROR_CODE
            }

            if (!ApiClient.requestUrlNotShowForceUpdateError.contains(requestUrl) &&
                !ApiClient.requestUrlNotShowUnAuthorizedAndForceUpdateErrorWithMethod.any {
                    it.key == requestUrl && it.value == method
                }
            ) {
                apiException?.let {
                    RxBus.publish(ForceUpdateEvent(it))
                }
            }
            return apiException
        }

        // maintenance mode
        HttpStatus.HTTP_UNAVAILABLE -> {
            val apiException = ApiException().apply {
                statusCode = HttpStatus.HTTP_UNAVAILABLE
            }

            RxBus.publish(MaintenanceModeEvent(apiException))

            return apiException
        }
    }

    response?.errorBody()?.let {
        return try {
            converter.convert(it)
        } catch (exception: Exception) {
            ApiException()
        }
    }

    return throwable
}