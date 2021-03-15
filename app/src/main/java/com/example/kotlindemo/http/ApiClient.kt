package com.example.kotlindemo.http

import android.bluetooth.BluetoothClass
import android.os.Build
import com.example.kotlindemo.BuildConfig
import com.example.kotlindemo.http.config.HttpLoggingInterceptor
import com.google.android.material.internal.ContextUtils
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.uniqlo.circle.architecture.data.api.NullOnEmptyConverterFactory
import com.uniqlo.circle.data.source.remote.network.CustomCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.Character.getName
import java.util.Collections
import java.util.Locale
import java.util.concurrent.TimeUnit

open class ApiClient private constructor(url: String? = null) {

    internal var token: String? = null
    internal var language: String? = null
    internal var region: String? = null
    internal var isFromUnitTest: Boolean = false

    private var baseUrl: String =
        if (url == null || url.isEmpty()) BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase() + "/" else url

    companion object : SingletonHolder<ApiClient, String>(::ApiClient) {

        internal const val USER_AGENT = "User-Agent"
        internal const val KEY_AB_TEST = "X_ABTEST_KEY"

        internal val requestUrlNotShowUnAuthorizedError = listOf(
            "${BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase()}/user/fr/logout",
            "${BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase()}/user/logout",
            "${BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase()}/user/push/token",
            "${BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase()}/outfit/visual-search"
        )
        internal val requestUrlNotShowForceUpdateError = listOf(
            "${BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase()}/user/fr/logout",
            "${BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase()}/user/logout",
            "${BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase()}/user/push/token",
            "${BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase()}/outfit/visual-search"
        )

        // Handle for case api not show UnAuthorized and ForceUpdate with method
        internal val requestUrlNotShowUnAuthorizedAndForceUpdateErrorWithMethod = mapOf(
            Pair(
                "${BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase()}/user/language",
                "GET"
            ),
            Pair(
                "${BuildConfig.BASE_API_URL + BuildConfig.BASE_API_VERSION + getLocaleSuffix().toLowerCase()}/user/region",
                "GET"
            )
        )

        //Use this url for detect item with Google Vision(v2)
        internal const val DETECT_ITEM_URL = "${BuildConfig.BASE_API_URL}v2/jp/outfit/detect-item"

        //Use this url for search with Google Vision(v2)
        internal const val SEARCH_ITEM_URL = "${BuildConfig.BASE_API_URL}v2/jp/outfit/detect-item"
        internal const val NEW_SEARCH_ITEM_URL = "${BuildConfig.BASE_API_URL}v2/jp/item/search"

        private const val API_TIMEOUT = 10L // 10 minutes
        private const val API_READ_TIMEOUT = 1L
        private const val OS_TYPE = "Android"

        internal fun getUserAgent() =
            "${BuildConfig.USER_AGENT_APP_NAME}-${getLocaleSuffix()}/${BuildConfig.VERSION_NAME} $OS_TYPE/${Build.VERSION.RELEASE} }"

        internal fun getUserAgentWebView() =
            " ${BuildConfig.USER_AGENT_APP_NAME}-${getLocaleSuffix()}/${BuildConfig.VERSION_NAME}"

        private fun getLocaleSuffix(): String {
            return Locale.JAPAN.country
        }
    }

    val service: ApiService
        get() {
            return createService()
        }

    private fun createService(): ApiService {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.interceptors().add(Interceptor { chain ->
            val original = chain.request()
            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)
            if (token != null) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            requestBuilder.addHeader("accept-language", language ?: "en")

            region?.let {
                requestBuilder.addHeader("Accept-Region", it)
            }

            if (!isFromUnitTest) {
                requestBuilder.addHeader(USER_AGENT, getUserAgent())
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        })

        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLog())
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        httpClientBuilder.interceptors().add(httpLoggingInterceptor)

        val client = httpClientBuilder
            .connectTimeout(API_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(API_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(API_READ_TIMEOUT, TimeUnit.MINUTES)
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
            .build()
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .serializeNulls()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(if (isFromUnitTest) baseUrl else BuildConfig.BASE_API_URL)
            .addConverterFactory(NullOnEmptyConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CustomCallAdapterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

}

class HttpLog : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        Timber.tag("HttpLog").d("HttpLogInfo: $message")
    }
}

/**
 * Use this class to create singleton object with argument
 */
open class SingletonHolder<out T, in A>(private var creator: (A?) -> T) {

    @kotlin.jvm.Volatile
    private var instance: T? = null

    /**
     * Generate instance for T class with argument A
     */
    fun getInstance(arg: A?, isFromUnitTest: Boolean = false): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator(arg)
                if (isFromUnitTest) {
                    (created as? ApiClient)?.isFromUnitTest = isFromUnitTest
                }
                instance = created
                created
            }
        }
    }

    /**
     * Clear current instance
     */
    fun clearInstance() {
        instance = null
    }
}
