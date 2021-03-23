package com.example.kotlindemo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.kotlindemo.R
import com.gyf.immersionbar.ImmersionBar

abstract  class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {
   internal lateinit var mBinding: DB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        ImmersionBar.with(this)
            .statusBarColor(R.color.title_blue)
            .statusBarAlpha(0.2f) //状态栏透明度，不写默认0.0f
            .fitsSystemWindows(true)
            .init()
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
