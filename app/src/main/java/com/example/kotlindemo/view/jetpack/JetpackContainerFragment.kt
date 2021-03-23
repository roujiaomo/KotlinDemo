package com.example.kotlindemo.view.jetpack

import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentJetpackContainerBinding
import com.uniqlo.circle.ui.base.BaseFragment

class JetpackContainerFragment : BaseFragment<FragmentJetpackContainerBinding>() {


    companion object {
        @JvmStatic
        fun newInstance() = JetpackContainerFragment()
    }

    override fun getLayoutId() = R.layout.fragment_jetpack_container


}