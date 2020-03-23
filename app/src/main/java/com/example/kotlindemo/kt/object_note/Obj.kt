package com.example.kotlindemo.kt.object_note

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

/**
 *  object
 *  声明 匿名内部类的写法
 */
class Obj {
    private lateinit var context:Context;
    //例如
    fun setEvent(){
        //第一种 当回调函数只有一个的时候 可省略
        Button(context).setOnClickListener {

        }

        //第二种 当有多个回调函数的时候
        //写法为 object : 所需对象类型  等同于 new 所需对象类型
        EditText(context).addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }
}