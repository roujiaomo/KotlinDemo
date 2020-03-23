package com.example.kotlindemo.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kotlindemo.constant.LoadStatus
import com.example.kotlindemo.widget.ProgressLoading
import java.lang.reflect.ParameterizedType

/**
 * 数据加载基类Fragment
 */
abstract class BaseDataFragment<VM : BaseViewModel> : Fragment() {

    private lateinit var mContext: Context
    lateinit var mViewModel: VM
    lateinit var mProgressLoading: ProgressLoading
    private var isFirstLoad = true // 是否第一次加载

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mProgressLoading = ProgressLoading.create(mContext)
        initViewModel()
        initLoadStatus() //数据请求状态
        initLiveDataObserve()
        initView()
        if (!isLazyLoad()) { //不是懒加载
            if (isFirstLoad) {
                initData() //网络请求数据
                isFirstLoad = isVisibleReload() //第一次加载后
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isLazyLoad()) { //是懒加载
            if (isFirstLoad) {
                initData() //网络请求数据
                isFirstLoad = isVisibleReload() //第一次加载后
            }
        }
    }

    private fun initViewModel() {
        mViewModel = if (isShareVMWithActivity()) {
            ViewModelProviders.of(this)[getVMClass()]
        } else {
            ViewModelProviders.of(activity!!)[getVMClass()]
        }
    }

    private fun initLoadStatus() {
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


    /**
     * 获取资源ID
     *
     * @return 布局资源ID
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化界面
     */
    abstract fun initView()

    abstract fun initData()

    abstract fun initLiveDataObserve()

    /**
     * 是否与Activity使用同一个ViewModel 默认false
     * (少数情况为true : 宿主Activity与 Fragment数据源相同)
     * @return
     */
    open fun isShareVMWithActivity(): Boolean {
        return false
    }

    /**
     * 是否每次可见都请求数据  true每次重新请求数据 false 只第一次可见加载数据
     * @return
     */
    abstract fun isVisibleReload(): Boolean

    /**
     * 是否懒加载 (默认false)
     * @return
     */
    open fun isLazyLoad(): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFirstLoad = true
    }

    /**
     *  直接在createPresenter中拿到当前类的泛型的class，利用反射制造一个对象并返回
     */
    private fun getVMClass(): Class<VM> {
        val type: ParameterizedType = this.javaClass.genericSuperclass as ParameterizedType
        var actualTypeArguments = type.actualTypeArguments
        return actualTypeArguments[0] as Class<VM>
    }

}