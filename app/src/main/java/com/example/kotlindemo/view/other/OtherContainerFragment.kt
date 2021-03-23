package com.example.kotlindemo.view.other

import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentOtherContainerBinding
import com.uniqlo.circle.ui.base.BaseFragment


class OtherContainerFragment : BaseFragment<FragmentOtherContainerBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = OtherContainerFragment()
    }

    override fun getLayoutId(): Int  = R.layout.fragment_other_container

}