package com.example.kotlindemo.kt.object_note

/**
 * 委托
 */
class By {

    /**
     * 常用:  by lazy
     *
     * 可以延迟声明类型
     *
     * 和lateinit区别 :
     *
     * lateinit 不可以初始化空值 和基础数据类型
     */
    val  name by lazy {
        val s:String = ""
        s.toInt()
        getType(1);
    }

    private fun getType(type:Int): Any {
        return when (type) {
            1 -> {
                1
            }
            2 -> {
                "1"
            }
            else -> {
                true
            }
        }
    }


}