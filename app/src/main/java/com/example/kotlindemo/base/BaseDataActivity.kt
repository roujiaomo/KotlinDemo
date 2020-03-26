package com.example.kotlindemo.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kotlindemo.constant.LoadStatus
import com.example.kotlindemo.widget.ProgressLoading
import com.gyf.immersionbar.ImmersionBar
import java.lang.reflect.ParameterizedType
import com.example.kotlindemo.R

abstract class BaseDataActivity<VM : BaseViewModel> : AppCompatActivity() {
    lateinit var mViewModel: VM
    lateinit var mProgressLoading: ProgressLoading
    lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .statusBarColor(R.color.title_blue)
            .statusBarAlpha(0.2f) //状态栏透明度，不写默认0.0f
            .fitsSystemWindows(true)
            .init()
        mContext = this
        mProgressLoading = ProgressLoading.create(mContext)
        setContentView(getLayoutId())
        initViewModel()
        if (isShowLoadStatus()) {
            initLoadStatus() //数据请求状态
        }
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

    /**
     * 是否显示网络请求时进度条(并发请求时使用, 或使用RxJava zip操作符)
     */
    fun isShowLoadStatus(): Boolean{
        return true
    }
    private fun initLiveDataObserve() {

    }

    private  fun initLoadStatus() {
        mViewModel.multiStatusLiveData.observe(this,
            Observer<Int?> { status ->
                when (status) {
                    LoadStatus.STATUS_LOADING -> {
                        mProgressLoading.showMessage("加载中")
                    }
                    LoadStatus.STATUS_UPLOADING -> {
                        mProgressLoading.showMessage("正在上传")
                    }
                    LoadStatus.STATUS_REQUEST -> {
                        mProgressLoading.showMessage("正在请求")
                    }
                    LoadStatus.STATUS_CONTENT ->
                        if (mProgressLoading.isShowing) {
                            mProgressLoading.hideLoading()
                        }
                    LoadStatus.STATUS_EMPTY -> {
                    }
                    LoadStatus.STATUS_ERROR ->
                        if (mProgressLoading.isShowing) {
                            mProgressLoading.hideLoading()
                        }
                    LoadStatus.STATUS_NO_NETWORK ->
                        if (mProgressLoading.isShowing) {
                            mProgressLoading.hideLoading()
                        }
                }
            })
    }

    //ViewModelProviders形式创建 未传给VM层引用
    open fun initViewModel() {
        mViewModel = ViewModelProviders.of(this)[getVMClass()]
    }

    /**
     *  中拿到当前类的泛型的class，利用反射制造一个对象并返回
     */
    private fun getVMClass(): Class<VM> {
        val type: ParameterizedType = this.javaClass.genericSuperclass as ParameterizedType
        var actualTypeArguments = type.actualTypeArguments
        return actualTypeArguments[0] as Class<VM>
    }
}
