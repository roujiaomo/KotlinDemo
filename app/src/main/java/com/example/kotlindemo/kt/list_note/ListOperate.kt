package com.example.kotlindemo.kt.list_note

/**
 * 集合常用操作及操作符
 */
class ListOperate{

    var dataList = mutableListOf<String>()
    var mList = arrayListOf<String>()

    /**
     * 过滤
     */
    fun filter(){
        //过滤出 符合{}条件的
        dataList.filter {
            it.length >=0
        }
        //过滤掉 符合{}条件的
        dataList.filterNot {
            it.length >=0
        }
    }



}