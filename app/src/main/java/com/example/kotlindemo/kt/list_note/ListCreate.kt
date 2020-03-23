package com.example.kotlindemo.kt.list_note

class ListCreate{
    //创建可变集合 (可增删 能遍历)
    var dataList = mutableListOf<String>()
    var mList = arrayListOf<String>()

    //创建不可变集合(不可增删 只能遍历)
    var finalList = listOf<String>()

    fun update(){
        dataList.add("")
        mList.add("")
        //无add方法
        finalList.last()
    }

}