package com.example.kotlindemo.kt.coroutine

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * 协程 : 一个基于Thread的线程框架 功能和java的线程池很接近
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


}