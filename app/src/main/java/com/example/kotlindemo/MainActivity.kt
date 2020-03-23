package com.example.kotlindemo

import android.util.Log
import com.example.kotlindemo.base.BaseActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_main;
    }

    override fun initView() {

    }

    override fun initData() {
        createCoroutine()
    }

    fun createCoroutine() {
        GlobalScope.launch {
            Log.d("Coroutine", "协程初始化完成，时间: " + System.currentTimeMillis())

            for (i in 1..3) {
                Log.d("Coroutine", "协程任务1打印第$i 次，时间: " + System.currentTimeMillis())
            }

            for (i in 1..3) {
                Log.d("Coroutine", "协程任务2打印第$i 次，时间: " + System.currentTimeMillis())
            }

        }
        for (i in 1..3) {
            Log.d("Coroutine", "主线程任务打印第$i 次，时间: " + System.currentTimeMillis())
        }


    }
}
