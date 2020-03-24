package com.example.kotlindemo.kt.coroutine.coroutine_create

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * suspend 挂起
 *
 * 非阻塞式挂起 : 不卡线程(切换线程), 因为协程的挂起是切换线程的, 切换线程就意味着线程是不卡的
 * 同理Thread多线程切换, 线程池切换也是不卡的, 也是非阻塞式挂起
 *
 * 阻塞式挂起 : 当Thread为单线程的时候, 在同一个Thread里操作即为阻塞的, 但是如果在一个Thread里new另外一个Thread就不是阻塞的
 * 协程不会阻塞的原因是, 单协程也可以去通过withContext()等方式切换线程 ( 见下方的 ThreadAndCoroutine() 方法 )
 *
 * 网络请求的阻塞不可避免 总是需要一个完整的线程去处理一个网络请求 Thread不行 协程的挂起也只是切换线程
 */
class Suspend{

    /**
     * 挂起: 讲当前线程切走 再切回来
     *
     * 挂起的概念不是暂停, 而是协程所在的线程从这行代码开始, 不再往下走协程里的内容了
     *
     * 例如 : 在下面的doSuspend()方法里
     * 当 withContext()调用后 , 在withContext()方法执行完以前, setData()方法不会执行
     *
     */

    fun doSuspend(){
       GlobalScope.launch(Dispatchers.Main) {//最外层声明了主线程
            //挂起函数 也可以声明在外面 但是要用suspend关键字声明方法(如下方的getData()方法)
            withContext(Dispatchers.IO){//主线程跳出launch方法体, withContext做的事切换到IO线程执行
                //子线程耗时操作
            }
            //withContext 执行完自动切回主线程 在主线程执行下面的
           setData()
        }
        //当挂起后, 主线程在这里继续进行, 该干嘛干嘛

    }

    /**
     * 挂起函数抽出来的写法
     */
    suspend fun getData(){
        withContext(Dispatchers.IO){
            //子线程耗时操作
        }
    }

    /**
     * 挂起函数执行完后 主线程执行
     */
    fun setData(){

    }

    /**
     * 对比单线程和单协程
     */
    fun ThreadAndCoroutine(){
        //单线程
        Thread{
            //doA()
            //doB() 两个方法在同一线程里 阻塞
        }

        //多线程
        Thread{
            //doA()
            Thread{
                //doB() 两个方法不在同一线程里 非阻塞
            }

        }


    }
}