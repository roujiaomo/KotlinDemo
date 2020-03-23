package com.example.kotlindemo.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.kotlindemo.R

abstract  class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    /**
     * 初始化布局id 方法体后的冒号为有返回值 , 冒号后为返回值类型
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化界面
     */
    abstract fun initView()


    /**
     * 初始化数据
     */
    abstract fun initData()
}
