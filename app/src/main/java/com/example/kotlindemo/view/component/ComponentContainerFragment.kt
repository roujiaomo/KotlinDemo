package com.example.kotlindemo.view.component

import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentComponentContainerBinding
import com.uniqlo.circle.ui.base.BaseFragment

class ComponentContainerFragment : BaseFragment<FragmentComponentContainerBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = ComponentContainerFragment()
    }

    override fun getLayoutId() = R.layout.fragment_component_container
}