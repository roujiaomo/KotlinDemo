package com.example.kotlindemo.view.source_code

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

    /**
     * 1.通过ViewModelProvider创建 ViewModel, 需要传入一个viewModelStore,FragmentActivity(ComponentActivity)中可以拿到这个viewModelStore, 通过一个单例类保存
     * 2.创建ViewModel时, 传入class类名, 通过class类名创建一个标识, 作为viewModelStore的 Key,存储进ViewModelStore中, 如果ViewModelStore中存在该Key, 则返回之前创建好的ViewModel
     * 3.Activity内, 监听 liveCycle,当生命周期为OnDestroy且非横竖屏切换时, 清空 Activity中的ViewModelStore, 如果为横竖屏切换则在存储ViewModel的单例类里获取该对象
     * 4.当ViewModelStore 清空时会调用ViewModel自身的onClear()方法
     */
    override fun onResume() {
        super.onResume()
        viewModelFactory = ViewModelProvider.NewInstanceFactory()
        viewModel = ViewModelProvider(requireActivity().viewModelStore, viewModelFactory).get(SampleViewModel::class.java)
        viewModel = ViewModelProvider(this.viewModelStore, viewModelFactory).get(SampleViewModel::class.java)

    }
}