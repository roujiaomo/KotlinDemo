package com.example.kotlindemo.view.component.test_fragment_livecycle

import android.util.Log
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentFirstAddChildBinding
import com.uniqlo.circle.ui.base.BaseFragment

class FirstAddChildFragment : BaseFragment<FragmentFirstAddChildBinding>() {
    private  val TAG = "Log_fragment_livecycle"


    companion object {
        fun newInstance() = FirstAddChildFragment()
    }

    override fun getLayoutId() = R.layout.fragment_first_add_child

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"-------------------------------------------")
        Log.d(TAG,"parentFragment:${parentFragment.toString()}")
        Log.d(TAG, "onResume:FirstAddChildFragment ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause:FirstAddChildFragment ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop:FirstAddChildFragment ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView:FirstAddChildFragment ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy:FirstAddChildFragment ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach:FirstAddChildFragment ")
    }
}