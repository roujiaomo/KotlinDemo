package com.example.kotlindemo

import android.util.Log
import com.example.kotlindemo.base.BaseActivity
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import okhttp3.Call
import okhttp3.Response
import java.lang.Exception

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main;
    }

    override fun initView() {

    }

    var token = ""
    override fun initData() {
        GlobalScope.launch(Dispatchers.Main) {
            getToken()

        }
    }


    suspend fun getToken(): String {
        Log.d("Coroutine", "getToken 开始执行")
        OkGo.get("http://v.juhe.cn/weather/index")
            .params("cityname", "成都")
            .params("key", "3ee5b1f1af26cb1baab51209970eac46")
            .execute(object : StringCallback() {
                override fun onSuccess(t: String?, call: Call?, response: Response?) {
                    Log.d("Coroutine", t)
                    token = t!!
                }

                override fun onError(call: Call?, response: Response?, e: Exception?) {
                    super.onError(call, response, e)
                    Log.d("Coroutine", e.toString())
                }
            })
        return token
    }

    suspend fun getResponse(token: String): String {
        Log.d("Coroutine", "getResponse 开始执行，时间:  ${System.currentTimeMillis()}")
        return "response"
    }

    fun setText(response: String) {
        Log.d("Coroutine", "setText 执行，时间:  ${System.currentTimeMillis()}")
    }


    fun createCoroutine() {

        GlobalScope.launch(Dispatchers.Unconfined) {
            Log.d("Coroutine", "协程初始化完成，时间: " + System.currentTimeMillis())

            for (i in 1..1000) {
                Log.d("Coroutine", "协程任务1打印第$i 次，时间: " + System.currentTimeMillis())
            }

        }
        GlobalScope.launch(Dispatchers.Unconfined) {
            Log.d("Coroutine", "协程初始化完成，时间: " + System.currentTimeMillis())
            for (i in 1..1000) {
                Log.d("Coroutine", "协程任务2打印第$i 次，时间: " + System.currentTimeMillis())
            }

        }

        for (i in 1..3) {
            Log.d("Coroutine", "主线程任务打印第$i 次，时间: " + System.currentTimeMillis())
        }

    }
}
