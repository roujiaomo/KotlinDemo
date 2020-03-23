package com.example.kotlindemo.kt.class_note

/**
 * 可继承的类和函数
 *
 * 若想一个类可以被继承必须加 open或 abstract关键字
 *
 * 区别 : 有无必须实现的抽象方法
 */
open class Base{
    open fun method(){

    }
}

open class BaseSon : Base() {
    //可重写 可不重写
    override fun method() {
        super.method()
    }
}


abstract class BaseA{
    abstract fun method()
}

class BaseASon : BaseA(){
    //必须重写
    override fun method() {
    }

}