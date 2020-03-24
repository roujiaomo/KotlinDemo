package com.example.kotlindemo.kt.coroutine.coroutine_create

import android.util.Log
import kotlinx.coroutines.*

/**
 * 创建协程
 */

class CouroutineCreate {

    /**
     * 通过 GlobalScope.launch方式
     *
     * launch需要传入的参数:
     * context : 协程的上下文 , 指定线程 (非必填 有默认值 Dispatchers.Default)
     * 可选参数 :Dispatchers.Default Dispatchers.Default默认的线程池  Dispatchers.IO
     * Dispatchers.Main (主线程) Dispatchers.Unconfined (没指定，就是在当前线程)
     *
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
     * GlobalScope.async与GlobalScope.launch 方法大致相同
     * 区别在于 返回值为 Deferred类型 Deferred是Job的子类 ,比job对了一个await()方法
     *
     * launch 和  async都不会阻塞线程 就算是delay()
     */
    fun createByAsync() {
        GlobalScope.launch(Dispatchers.Unconfined) {
            val deferred = GlobalScope.async {
                delay(1000L)
                return@async "taonce"
            }
            //挂起协程 但是不会阻塞线程
            val result = deferred.await()
        }
    }

    /**
     * 通过 runBlocking
     * runBlocking启动的协程任务会阻断当前线程，直到该协程执行结束。
     * runBlocking 和 launch 区别的地方就是 runBlocking 的 delay 方法是可以阻塞当前的线程的，和Thread.sleep() 一样
     */
    fun createByRunBlocking() {
        runBlocking {
            // 阻塞1s
            delay(1000L)
        }
        // 阻塞2s
        Thread.sleep(2000L)
    }




}