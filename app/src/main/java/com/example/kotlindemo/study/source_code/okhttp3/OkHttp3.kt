package com.example.kotlindemo.study.source_code.okhttp3

import okhttp3.*
import java.io.IOException


class OkHttp3 {


    fun newOkHttp3Call() {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        val okHttpClient = okHttpClientBuilder.build()
        val request = Request.Builder().build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                TODO("Not yet implemented")
            }
        })

    }
}