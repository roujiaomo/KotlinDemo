package com.example.kotlindemo.kt.list_note

import android.util.Log

class ForList{

    var dataList = mutableListOf<String>()
    var mList = arrayListOf<String>()

    fun doFor(){
        //普通for
        for(index in 0 until 100){

        }

        //集合for 带索引
        for(index in dataList.indices){

        }

        //集合for 不带索引
        for(data in dataList){
            Log.d("", data)
        }

        //集合forEach
        mList.forEach {
            Log.d("", it)
        }
    }


}