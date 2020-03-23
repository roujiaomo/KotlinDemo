package com.example.kotlindemo.kt.coroutine

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * 协程 : 轻量级线程  异步逻辑 同步代码
 */
class Coroutine {


    /**
     * 协程流程
     *
     * coroutine.create(Function) 创建协程
     * coroutine.status(coroutine-Object) 查询状态
     * coroutine.yield(Value to Yield) 挂起协程
     * coroutine.resume(coroutine-Object) 恢复协程
     */

    /**
     * 挂起函数 : 以suspend修饰的函数
     *
     * 挂起函数只能在其他挂起函数或协程中调用
     *
     * 挂起函数的语义:
     *
     * 挂起时, 指主线程被挂起, 去进行异步操作 : 协程挂起
     * 当异步操作完成后, 主线程恢复 : 协程恢复
     */
    suspend fun getData(): String {
        //异步操作
        return ""
    }

    //界面主线程调用
    suspend fun doInUi() {
        try {
            var data = getData()  //概念: 这是一个挂起点
        } catch (t: Throwable) {

        }
    }

    /**
     * 创建协程
     */
    fun createCoroutine() {
        GlobalScope.launch {
            Log.d("AA", "协程初始化完成，时间: " + System.currentTimeMillis())

            for (i in 1..3) {
                Log.d("AA", "协程任务1打印第$i 次，时间: " + System.currentTimeMillis())
            }

            delay(500)

            for (i in 1..3) {
                Log.d("AA", "协程任务2打印第$i 次，时间: " + System.currentTimeMillis())
            }

        }

    }


}