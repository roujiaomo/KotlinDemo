package com.example.kotlindemo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * 模型： ViewGroup在最下层，子View在ViewGroup上层
 *
 * 一组事件是由Action_Down开始，以Action_Up或Cancel结束
 */

class EventView(context: Context,attrs: AttributeSet) : View(context,attrs) {


    /**
     * 消费一组事件
     * 返回true时，代表自己消费该组全部事件(ACTION_DOWN 开始的全部事件)
     *
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //因为一组事件由ACTION_DOWN开始
        //只有在ACTION_DOWN时，返回return true 才有意义
        //当其他View的onTouchEvent()方法return true(消费事件)时，自己就收不到后续事件了
//        if(event?.actionMasked == MotionEvent.ACTION_DOWN){
//            return true
//        }
        return true
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        val flag = super.dispatchTouchEvent(event)
        return flag
    }


}