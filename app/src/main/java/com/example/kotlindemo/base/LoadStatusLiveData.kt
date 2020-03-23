package com.example.kotlindemo.base

import androidx.lifecycle.LiveData
import com.bumptech.glide.load.engine.Engine
import com.example.kotlindemo.constant.LoadStatus

class LoadStatusLiveData: LiveData<Int>() {

    override fun onActive() {
        super.onActive()
    }

    override fun onInactive() {
        super.onInactive()
    }


    /**
     * 主线程更新数据
     */
    fun setStatus(status: Int) {
       value = status
    }

    fun getStatus(): Int {
        return value ?: LoadStatus.STATUS_ERROR
    }

    /**
     * 子线程更新数据
     */
    fun post(status: Int) {
        postValue(status)
    }
}