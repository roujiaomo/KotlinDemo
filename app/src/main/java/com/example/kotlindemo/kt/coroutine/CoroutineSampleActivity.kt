package com.example.kotlindemo.kt.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.kotlindemo.R
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import kotlinx.android.synthetic.main.activity_coroutine_sample.*
import kotlinx.coroutines.*
import okhttp3.Call
import okhttp3.Response
import java.lang.Exception

class CoroutineSampleActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "CoroutineSampleActivity"
    private lateinit var responseData: String //返回数据
    override fun onClick(v: View?) {
        when (v?.id) {
            //三种创建方式
            R.id.btnCreateByLaunch -> createByLaunch()
            R.id.btnCreateByAsync -> createByAsync()
            R.id.btnCreateByRunBlocking -> createByRunBlocking()
            R.id.btnGetDataByAsync -> {
                GlobalScope.launch(Dispatchers.Main) {
                    getDataByAsync() //获取数据
                    Log.d(TAG, "返回数据: ${responseData}")
                }
            }
        }
    }

    /**
     * 通过GlobalScope.launch 创建
     *
     * 设置 Dispatchers.Default / Dispatchers.IO  :
     * 日志顺序 : 1 -> 4 -> 2 -> 3
     * launch方式下使用两个参数创建的协程, 默认不阻塞线程 ,
     * 协程内是子线程, 协程的创建切换了线程, 程序会同时往下运行(直接走到 4)
     *
     * 注 : delay()函数执行完后的内容默认是在子线程
     *
     * 设置Dispatchers.Main / Dispatchers.Unconfined(通常不会去new Thread, 这里可以当做主线程)
     * 日志顺序 : 1 -> 2 -> 4 -> 3
     * 因为创建协程的同时没有切换线程, 所以按顺序执行
     *
     * 如果不设置 Dispatchers.Main , 协程的方法体里一定都是运行在子线程(非主线程)
     */
    private fun createByLaunch() {
        Log.d(TAG, "1 .创建协程前 in ${Thread.currentThread()}")
        GlobalScope.launch(Dispatchers.Unconfined) {
            Log.d(TAG, "2. 协程delay前 in ${Thread.currentThread()}")
            delay(1000)
            Log.d(TAG, "3.  协程delay后 in ${Thread.currentThread()}")
        }
        Log.d(TAG, " 4. 协程方法体后 in ${Thread.currentThread()}")
    }

    /**
     * 通过GlobalScope.async创建  大体和launch方式相同
     * 比launch方式多了一个await函数 ,该函数用于返回值(参照 getDataByAsync()) 方便应用于子协程
     *
     *设置 Dispatchers.Default / Dispatchers.IO  : 与launch相同
     *
     *设置Dispatchers.Main / Dispatchers.Unconfined(通常不会去new Thread, 这里可以当做主线程)
     * 日志顺序也为 : 1 -> 4 -> 2 -> 3
     */
    private fun createByAsync() {
        Log.d(TAG, "1 .创建协程前 in ${Thread.currentThread()}")
        GlobalScope.async(Dispatchers.Main) {
            Log.d(TAG, "2. 协程delay前 in ${Thread.currentThread()}")
            delay(1000)
            Log.d(TAG, "3.  协程delay后 in ${Thread.currentThread()}")
        }
        Log.d(TAG, " 4. 协程方法体后 in ${Thread.currentThread()}")
    }

    /**
     * 通过Async获取数据
     */
    private suspend fun getDataByAsync() {
        Log.d(TAG, "1 .创建协程前 in ${Thread.currentThread()}")
        responseData = GlobalScope.async(Dispatchers.Default) {
            Log.d(TAG, "2. 协程体内 in ${Thread.currentThread()}")
            return@async getData()
        }.await()
        Log.d(TAG, " 3. 协程方法体后 in ${Thread.currentThread()}")
    }

    /**
     * 通过runBlocking方式
     * 该方式和launch与async最大的区别就是, 它可以阻塞线程,
     * 其他两种如果不声明在主线程里, 是可以跳过代码块继续主线程逻辑
     * 即runBlocking声明的代码块, 必须执行完, 原始线程才可以继续进行
     *
     * 这里需要注意, runBlocking不能声明Dispatchers.Main, 否则会直接堵死
     *
     */
    private fun createByRunBlocking() {
        Log.d(TAG, "1 .创建协程前 in ${Thread.currentThread()}")
        runBlocking(Dispatchers.Unconfined) {
            Log.d(TAG, "2. 协程delay前 in ${Thread.currentThread()}")
            delay(1000)
            Log.d(TAG, "3.  协程delay后 in ${Thread.currentThread()}")
        }
        Log.d(TAG, " 4. 协程方法体后 in ${Thread.currentThread()}")
    }

    suspend fun getData(): String {
        delay(3000)
        return "返回数据"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_sample)
        btnCreateByLaunch.setOnClickListener(this)
        btnCreateByAsync.setOnClickListener(this)
        btnGetDataByAsync.setOnClickListener(this)
        btnCreateByRunBlocking.setOnClickListener(this)
    }
}
