package com.example.kotlindemo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

import com.example.kotlindemo.R
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnToLogin.setOnClickListener(this)
        btnToRegister.setOnClickListener(this)
    }

    /**
     * 传递数据 : 通过bundle或者内置argument
     */
    override fun onClick(v: View?) {

        when(v?.id){
            R.id.btnToRegister -> {
                findNavController().navigate(R.id.registerFragment)
            }
            R.id.btnToLogin -> {
                val action =
                    WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
                    .setData("TeaOf1995@Gamil.com")
                findNavController().navigate(action)
            }
        }
    }

}
