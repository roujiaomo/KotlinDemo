package com.uniqlo.circle.data.source.remote.network

import com.example.kotlindemo.http.*
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

/**
 *
 * @author at-hoavo.
 */
interface CustomCallback<T> {

    /** Called for [200] responses.  */
    fun success(call: Call<T>, response: Response<T>)

    /** Called for [401] responses.  */
    fun unauthenticated(t: Throwable)

    /** Called for [400, 500) responses, except 401.  */
    fun clientError(t: Throwable)

    /** Called for [500, 600) response.  */
    fun serverError(t: Throwable)

    /** Called for network errors while making the call.  */
    fun networkError(e: Throwable)

    /** Called for unexpected errors while making the call.  */
    fun unexpectedError(t: Throwable)
}

/**
 * CustomCall
 */
interface CustomCall<T> {

    /**
     * Cancel call
     */
    fun cancel()

    /**
     * Enqueue call
     */
    fun enqueue(callback: CustomCallback<T>)

    /**
     * Execute call
     */
    fun execute(): Response<T>

    /**
     * Clone
     */
    fun clone(): CustomCall<T>

    /**
     * Request call
     */
    fun request(): Request

    /**
     * Check Call is canceled
     */
    fun isCanceled(): Boolean

    /**
     * Check Call is executed
     */
    fun isExecuted(): Boolean
}

internal class CustomCallAdapter<T>(private val call: Call<T>, private val retrofit: Retrofit) : CustomCall<T> {

    override fun execute() = call.execute()

    override fun clone() = this

    override fun request() = Request.Builder().build()

    override fun isCanceled() = call.isCanceled

    override fun isExecuted() = call.isExecuted

    override fun cancel() {
        call.cancel()
    }

    override fun enqueue(callback: CustomCallback<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val code = response.code()
                try {
                    when (code) {
                        HttpURLConnection.HTTP_OK -> callback.success(call, response)
                        HttpURLConnection.HTTP_UNAUTHORIZED -> {
                            val converter: Converter<ResponseBody, ApiException> =
                                retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
                            val responseAfterConvert = converter.convert(response.errorBody())?.apply {
                                statusCode = HttpURLConnection.HTTP_UNAUTHORIZED
                            }

                            val url = response.raw().request.url.toString()
                            val method = response.raw().request.method
                            if (!ApiClient.requestUrlNotShowUnAuthorizedError.contains(url) && !ApiClient.requestUrlNotShowUnAuthorizedAndForceUpdateErrorWithMethod.any {
                                    it.key == url && it.value == method
                                }) {

                                responseAfterConvert?.let {
                                    RxBus.publish(UnAuthorizeEvent(it))
                                }
                            }

                            responseAfterConvert?.let {
                                callback.serverError(it)
                            }
                        }

                        ApiException.FORCE_UPDATE_ERROR_CODE -> {
                            val converter: Converter<ResponseBody, ApiException> =
                                retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
                            val responseAfterConvert = converter.convert(response.errorBody())?.apply {
                                statusCode = ApiException.FORCE_UPDATE_ERROR_CODE
                            }

                            val url = response.raw().request.url.toString()
                            val method = response.raw().request.method.toString()

                            if (!ApiClient.requestUrlNotShowForceUpdateError.contains(url) && !ApiClient.requestUrlNotShowUnAuthorizedAndForceUpdateErrorWithMethod.any {
                                    it.key == url && it.value == method
                                }) {

                                responseAfterConvert?.let {
                                    RxBus.publish(ForceUpdateEvent(it))
                                }
                            }

                            responseAfterConvert?.let {
                                callback.serverError(it)
                            }
                        }

                        HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                            val converter: Converter<ResponseBody, ApiException> =
                                retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
                            val responseAfterConvert = converter.convert(response.errorBody())

                            responseAfterConvert?.let {
                                callback.serverError(it)
                            }
                        }

                        HttpURLConnection.HTTP_BAD_REQUEST -> {
                            val converter: Converter<ResponseBody, ApiException> =
                                retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
                            val responseAfterConvert = converter.convert(response.errorBody())?.apply {
                                statusCode = HttpURLConnection.HTTP_BAD_REQUEST
                            }

                            responseAfterConvert?.let {
                                callback.clientError(it)
                            }
                        }

                        HttpURLConnection.HTTP_NOT_ACCEPTABLE -> {
                            val converter: Converter<ResponseBody, ApiException> =
                                retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
                            val responseAfterConvert = converter.convert(response.errorBody())

                            responseAfterConvert?.let {
                                callback.clientError(it)
                            }
                        }
                        HttpsURLConnection.HTTP_NOT_FOUND -> {
                            val converter: Converter<ResponseBody, ApiException> =
                                retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
                            val responseAfterConvert = converter.convert(response.errorBody())

                            responseAfterConvert?.let {
                                callback.clientError(it)
                            }
                        }

                        HttpsURLConnection.HTTP_FORBIDDEN -> {
                            val converter: Converter<ResponseBody, ApiException> =
                                retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
                            val responseAfterConvert = converter.convert(response.errorBody())?.apply {
                                statusCode = HttpsURLConnection.HTTP_FORBIDDEN
                            }


                            responseAfterConvert?.let {
                                callback.clientError(it)
                            }
                        }

                        HttpsURLConnection.HTTP_CONFLICT -> {
                            val converter: Converter<ResponseBody, ApiException> =
                                retrofit.responseBodyConverter(ApiException::class.java, arrayOfNulls<Annotation>(0))
                            val responseAfterConvert = converter.convert(response.errorBody())?.apply {
                                statusCode = HttpsURLConnection.HTTP_CONFLICT
                            }

                            responseAfterConvert?.let {
                                callback.clientError(it)
                            }
                        }

                        //Todo: Handle another status code
                        else -> callback.unexpectedError(Throwable("Error unknow"))
                    }
                } catch (e: EOFException) {
                    callback.unexpectedError(e)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if (t is IOException) {
                    when (t) {
                        is UnknownHostException -> {
                            val apiException = ApiException("", mutableListOf())
                            apiException.statusCode = ApiException.NETWORK_ERROR_CODE
                            callback.networkError(apiException)
                        }
                        is SocketTimeoutException -> {
                            val apiException = ApiException("", mutableListOf())
                            apiException.statusCode = HttpURLConnection.HTTP_CLIENT_TIMEOUT
                            callback.networkError(apiException)
                        }

                        is ConnectException -> {
                            val apiException = ApiException("", mutableListOf())
                            apiException.statusCode = ApiException.NETWORK_ERROR_CODE
                            callback.networkError(apiException)
                        }

                        else -> callback.networkError(t)
                    }
                } else {
                    callback.unexpectedError(t)
                }
            }
        })
    }
}
