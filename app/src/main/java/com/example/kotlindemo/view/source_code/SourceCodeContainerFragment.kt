package com.example.kotlindemo.view.source_code

import androidx.lifecycle.ViewModelProvider
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentSourceCodeContainerBinding
import com.example.kotlindemo.view.main.MainViewModel
import com.uniqlo.circle.extension.activityViewModelProvider
import com.uniqlo.circle.ui.base.BaseFragment

class SourceCodeContainerFragment : BaseFragment<FragmentSourceCodeContainerBinding>() {
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    companion object {
        @JvmStatic
        fun newInstance() = SourceCodeContainerFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_source_code_container

    override fun onResume() {
        super.onResume()
        viewModelFactory = ViewModelProvider.NewInstanceFactory()
        viewModel = activityViewModelProvider(viewModelFactory)
    }
}