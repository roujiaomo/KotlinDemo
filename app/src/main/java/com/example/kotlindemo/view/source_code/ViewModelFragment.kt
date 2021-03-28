package com.example.kotlindemo.view.source_code

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentViewModelBinding
import com.uniqlo.circle.ui.base.BaseFragment

class ViewModelFragment : BaseFragment<FragmentViewModelBinding>() {

    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SampleViewModel

    companion object {
        fun newInstance() = ViewModelFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_view_model

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        bindLiveDataObserver()
    }

    /**
     * 1.通过ViewModelProvider创建 ViewModel, 需要传入一个viewModelStore,FragmentActivity(ComponentActivity)中可以拿到这个viewModelStore, 通过一个单例类保存
     * 2.创建ViewModel时, 传入class类名, 通过class类名创建一个标识, 作为viewModelStore的 Key,存储进ViewModelStore中, 如果ViewModelStore中存在该Key, 则返回之前创建好的ViewModel
     * 3.Activity内, 监听 liveCycle,当生命周期为OnDestroy且非横竖屏切换时, 清空 Activity中的ViewModelStore, 如果为横竖屏切换则在存储ViewModel的单例类里获取该对象
     * 4.当ViewModelStore 清空时会调用ViewModel自身的onClear()方法
     * 5.数据共享的效果，例如Fragment与宿主Activity，只需要Fragment传入宿主Activity的viewModelStore ，即requireActivity().viewModelStore，即可获取到Activity的ViewModel里的数据
     * 6.一个界面有多个ViewModel，都会存储到ViewModelStore中，通过不同的key（ViewModel类名生成）取出不同的ViewModel
     */
    private fun initViewModel() {
        viewModelFactory = ViewModelProvider.NewInstanceFactory()
        viewModel = ViewModelProvider(requireActivity().viewModelStore, viewModelFactory).get(SampleViewModel::class.java)
        viewModel = ViewModelProvider(this.viewModelStore, viewModelFactory).get(SampleViewModel::class.java)

    }

    private fun bindLiveDataObserver() {
        viewModel.getData.observe(viewLifecycleOwner, Observer{ observeerValue->

        })
    }


}