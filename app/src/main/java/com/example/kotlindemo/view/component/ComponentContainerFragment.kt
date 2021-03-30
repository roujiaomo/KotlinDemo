package com.example.kotlindemo.view.component

import android.os.Bundle
import android.util.Log
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentComponentContainerBinding
import com.example.kotlindemo.view.component.test_fragment_livecycle.FirstAddChildFragment
import com.example.kotlindemo.view.component.test_fragment_livecycle.FirstReplaceChildFragment
import com.example.kotlindemo.view.component.test_fragment_livecycle.SecondAddChildFragment
import com.uniqlo.circle.extension.addChildFragment
import com.uniqlo.circle.extension.replaceChildFragment
import com.uniqlo.circle.ui.base.BaseFragment

class ComponentContainerFragment : BaseFragment<FragmentComponentContainerBinding>() {

    private val TAG = "Log_fragment_livecycle"

    companion object {
        fun newInstance() = ComponentContainerFragment()
    }

    override fun getLayoutId() = R.layout.fragment_component_container

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        replaceChildFragment(
            getContainerId(),
            FirstReplaceChildFragment.newInstance()
        )
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "-------------------------------------------")
        Log.d(TAG, "parentFragment:${parentFragment.toString()}")
        Log.d(TAG, "onResume:$TAG ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause:$TAG ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop:$TAG ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView:$TAG ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy:$TAG ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach:$TAG ")
    }

    fun openAddFirstFragment() {
        addChildFragment(
            getContainerId(),
            FirstAddChildFragment.newInstance(),
            FirstAddChildFragment::javaClass.name
        )
    }

    fun openSecondFirstFragment() {
        addChildFragment(
            getContainerId(),
            SecondAddChildFragment.newInstance(),
            SecondAddChildFragment::javaClass.name
        )
    }

    override fun getContainerId() = R.id.frame_component_container
}