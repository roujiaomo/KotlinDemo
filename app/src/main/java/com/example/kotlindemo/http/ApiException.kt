package com.example.kotlindemo.http

import com.google.gson.annotations.SerializedName

/**
 * Use this file to handle error from api
 */
data class ApiException(
    @field:SerializedName("message") val messageError: String = "",
    @field:SerializedName("errors") val errors: List<String> = emptyList(),
    @field:SerializedName("status") val status: String = "",
    @field:SerializedName("latest_version") val latestVersion: String = "",
    @field:SerializedName("update_url") val updateUrl: String = ""
) : Throwable(messageError) {

    companion object {
        internal const val FORCE_UPDATE_ERROR_CODE = 426
        internal const val NETWORK_ERROR_CODE = 700
    }

    var statusCode: Int? = null
}
