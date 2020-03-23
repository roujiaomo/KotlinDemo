package com.example.kotlindemo.kt.coroutine.coroutine_create

import android.util.Log
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 四种方式创建协程
 */

class CouroutineCreate {

    /**
     * 通过 GlobalScope.launch方式
     *
     * launch需要传入的参数:
     * context : 协程的上下文 , 指定线程 (非必填 有默认值 Dispatchers.Default)
     * 可选参数 : Dispatchers.Default   Dispatchers.IO  Dispatchers.Main (主线程) Dispatchers.Unconfined (没指定，就是在当前线程)
     * CoroutineStart : 协程的启动模式
     * 可选参数 : CoroutineStart.DEFAULT 立即执行 CoroutineStart.LAZY 需要延迟执行(手动调用start)
     * block : 闭包方法体，定义协程内需要执行的操作
     *
     * GlobalScope.launch的返回值 job:
     * 可以把 Job 看成协程对象本身，协程的操作方法都在 Job 身上
     * job.start() - 启动协程，除了 lazy 模式，协程都不需要手动启动
     * job.join() - 等待协程执行完毕
     * job.cancel() - 取消一个协程
     * job.cancelAndJoin() - 等待协程执行完毕然后再取消
     */
    fun createByLaunch() {
        val job = GlobalScope.launch(
            context = Dispatchers.Unconfined
            , start = CoroutineStart.DEFAULT
        ) {
            //具体操作
        }
        //当设置为延迟运行时, 需要手动启动协程
        job.start()

    }

    /**
     * 通过 GlobalScope.async 形式
     *
     * 
     */
    fun createByAsync() {


    }

}