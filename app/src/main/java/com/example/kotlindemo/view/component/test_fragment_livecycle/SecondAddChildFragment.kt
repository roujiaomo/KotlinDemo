package com.example.kotlindemo.view.component.test_fragment_livecycle

import android.util.Log
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentSecondAddChildBinding
import com.uniqlo.circle.ui.base.BaseFragment

class SecondAddChildFragment : BaseFragment<FragmentSecondAddChildBinding>() {
    private  val TAG = "Log_fragment_livecycle"


    companion object {
        fun newInstance() = SecondAddChildFragment()
    }

    override fun getLayoutId() = R.layout.fragment_second_add_child

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"-------------------------------------------")
        Log.d(TAG,"parentFragment:${parentFragment.toString()}")
        Log.d(TAG, "onResume: SecondAddChildFragment ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause:SecondAddChildFragment ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop:SecondAddChildFragment ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView:SecondAddChildFragment ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy:SecondAddChildFragment ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach:SecondAddChildFragment ")
    }
}