package com.example.kotlindemo.widget

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.utils.visible
import com.jakewharton.rxbinding2.view.RxMenuItem.visible

/**
 * ProgressLoading
 */
class ProgressLoading private constructor(context: Context, theme: Int) : Dialog(context, theme) {

    private var animDrawable: AnimationDrawable? = null

    companion object {
        /*
            创建加载对话框
         */
        fun create(context: Context): ProgressLoading {
            //样式引入
            val mDialog = ProgressLoading(context, R.style.LightProgressDialog)
            //设置布局
            mDialog.apply {
                setContentView(R.layout.common_progress_dialog)
                setCancelable(true)
                setCanceledOnTouchOutside(false)
                window?.attributes?.gravity = Gravity.CENTER

                val lp = window?.attributes
                lp?.dimAmount = 0.2f
                //设置属性
                window?.attributes = lp
                //获取动画视图
                val loadingView = findViewById<ImageView>(R.id.iv_loading)
                animDrawable = loadingView.background as AnimationDrawable
            }

            return mDialog
        }
    }

    /*
        显示加载对话框，动画开始
     */
    fun showLoading() {
        super.show()
        animDrawable?.start()
    }

    /*
        显示加载信息
     */
    fun showMessage(msg: String) {
        showLoading()
        findViewById<TextView>(R.id.tv_Message).apply {
            text = msg
            visible()
        }
    }

    /*
        隐藏加载对话框，动画停止
     */
    fun hideLoading() {
        super.dismiss()
        animDrawable?.stop()
    }
}
