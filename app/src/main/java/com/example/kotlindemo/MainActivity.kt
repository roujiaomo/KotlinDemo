package com.example.kotlindemo

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.webkit.*
import androidx.annotation.RequiresApi
import com.example.kotlindemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class MainActivity : BaseActivity() {

     val webUrl :String = "https://stage-api.stylehint.com/v1/auth-scopes?brand=UNIQLO&region=JP"

    override fun getLayoutId(): Int {
        return R.layout.activity_main;
    }

    override fun initView() {

    }

//    ▿ 0 : 2 elements
//      - key : “client-id”
//      - value : “bYY8tDSJtsyNKO5apY6WO9GIxuCcBTaO”
//     ▿ 1 : 2 elements
//      - key : “Authorization”
//      - value : “”
//     ▿ 2 : 2 elements
//      - key : “Content-Type”
//      - value : “application/x-www-form-urlencoded”
//     ▿ 3 : 2 elements
//      - key : “Accept-Region”
//      - value : “US”
//     ▿ 4 : 2 elements
//      - key : “X-ABTEST-KEY-OUTFIT-EXPLORE”
//      - value : “3J0ZR34D”
//     ▿ 5 : 2 elements
//      - key : “User-Agent”
//      - value : “Circle-JP/2.10.0 iOS/14.4 x86_64"
//     ▿ 6 : 2 elements
//      - key : “X-ABTEST-KEY-OUTFIT-FEED”
//      - value : “Variant A”
//     ▿ 7 : 2 elements
//      - key : “Accept-Language”
//      - value : “US-en”

    override fun initData() {
        btn_load.setOnClickListener {
            with(webView){
               settings.useWideViewPort = true;
                settings.loadWithOverviewMode = true;
                settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL;
                webViewClient = LoginEmailWebViewClient()
                loadUrl(webUrl)
            }
        }
    }

    private inner class LoginEmailWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return true
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request?.url?.toString()
            return true
        }

        override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
            super.onReceivedError(view, errorCode, description, failingUrl)
        }

        override fun shouldInterceptRequest(
            view: WebView?,
            request: WebResourceRequest?
        ): WebResourceResponse? {
            return if(request?.url.toString() == webUrl){
                getAuthScope(request?.url.toString())
            }else{
                super.shouldInterceptRequest(view, request)
            }
        }
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?
        ) {
            super.onReceivedHttpError(view, request, errorResponse)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        private fun setBlankWebView(webView: WebView?) {
            webView?.loadUrl("about:blank")
        }

    }

    private fun getAuthScope(webUrl:String):WebResourceResponse {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        // Current request url will redirect other path , The final response cannot get the 【location】 from the response header element
        // So need to cancel okHttp redirect config
        okHttpClientBuilder.followRedirects(true)
        val okHttpClient = okHttpClientBuilder.build()
        val request = Request.Builder()
            .url(webUrl)
        request.addHeader("client-id", "bYY8tDSJtsyNKO5apY6WO9GIxuCcBTaO")
            .addHeader("Authorization", "")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Accept-Region", "US")
            .addHeader("User-Agent", "Circle-JP/2.10.0 iOS/14.4 x86_64")
            .addHeader("Accept-Language",  "US-en")
            .addHeader("X-ABTEST-KEY-OUTFIT-EXPLORE",  "3J0ZR34D")
            .addHeader("X-ABTEST-KEY-OUTFIT-FEED",  "Variant A")

       val call = okHttpClient.newCall(request.build())
        var response:Response? =null
        try {
             response = call.execute()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d(TAG, "onResponse: ")
        var contentType = response?.header("Content-Type", "text/html").toString().split(";")[0]
        contentType = "text/html"
        return  WebResourceResponse(
            contentType,
            null,
            response?.body?.byteStream()
        )
    }

    private  val TAG = "MainActivity"
}
