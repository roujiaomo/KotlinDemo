package com.example.kotlindemo.view.component.test_fragment_livecycle

import android.os.Bundle
import android.util.Log
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentFirstReplaceChildBinding
import com.example.kotlindemo.view.component.ComponentContainerFragment
import com.uniqlo.circle.ui.base.BaseFragment

class FirstReplaceChildFragment : BaseFragment<FragmentFirstReplaceChildBinding>() {
    private val TAG = "Log_fragment_livecycle"


    companion object {
        fun newInstance() = FirstReplaceChildFragment()
    }

    override fun getLayoutId() = R.layout.fragment_first_replace_child

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.btnToFirstFragment.setOnClickListener {
            parentFragment?.let {
                (it as? ComponentContainerFragment)?.openAddFirstFragment()
            }
        }
        binding.btnToSecondFragment.setOnClickListener {
            parentFragment?.let {
                (it as? ComponentContainerFragment)?.openSecondFirstFragment()
            }

        }

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "-------------------------------------------")
        Log.d(TAG, "parentFragment:${parentFragment.toString()}")
        Log.d(TAG, "onResume:FirstReplaceChildFragment ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause:FirstReplaceChildFragment ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop:FirstReplaceChildFragment ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView:FirstReplaceChildFragment ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy:FirstReplaceChildFragment ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach:FirstReplaceChildFragment ")
    }
}