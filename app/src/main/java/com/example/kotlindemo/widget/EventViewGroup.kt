package com.example.kotlindemo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import com.example.kotlindemo.R

/**
 * 注意super调用的方法全部是指原生View或ViewGroup类
 * 核心在于 dispatchTouchEvent()方法中的for循环，以及在dispatchTouchEvent()方法最终会调用onTouchEvent(）
 *
 * 单ViewGroup模型：
 * 触摸屏幕后，事件先传到最外层ViewGroup的 dispatchTouchEvent()，默认调用super.dispatchTouchEvent()
 * 在super.dispatchTouchEvent()中判断自身ViewGroup的onInterceptTouchEvent(默认返回false)，
 * 最终调用自己的
 *
 * 父ViewGroup 子View模型：
 *
 * 触摸屏幕后，事件先传到最外层ViewGroup的 dispatchTouchEvent()，默认调用super.dispatchTouchEvent()
 * 在super.dispatchTouchEvent()中判断自身ViewGroup的onInterceptTouchEvent(默认返回false)，
 * 继续遍历子View的dispatchTouchEvent()，即事件向下分发的模型 ：
 * 父dispatchTouchEvent()->子dispatchTouchEvent() （值为false，事件未分发）
 *
 * 遍历子View的dispatchTouchEvent()最终会调用onTouchEvent()：
 *
 * 如果子View未消费：（onTouchEvent()返回false ，因为View没有onInterceptTouchEvent方法 ）
 * 回到最外层的ViewGroup的onTouchEvent()，即事件向上传递的模型 ：
 * 子onTouchEvent()->父onTouchEvent() （值为false，未消费）
 *
 * 如果子View消费： （onTouchEvent()返回true）
 * 则子View的dispatchTouchEvent()返回true，事件向上传递：
 * 子dispatchTouchEvent()->父dispatchTouchEvent() （值为true，事件分发成功）
 *
 * 备注：优先级 ：onTouch() > onTouchEvent() > onClick()
 */
class EventViewGroup(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.layout(20, 20, 400, 400)
            child.setBackgroundColor(context.resources.getColor(R.color.colorPrimaryDark))
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val flag = super.onTouchEvent(event)
        return flag
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val flag = super.dispatchTouchEvent(ev)
        return flag
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val flag = super.onInterceptTouchEvent(ev)
        return flag
    }
}