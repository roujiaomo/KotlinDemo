package com.example.kotlindemo.kt.class_note

/**
 * 构造函数
 *
 * 在 Kotlin 中的一个类可以有一个主构造函数以及一个或多个次构造函数。
 * 主构造函数是类头的一部分：它跟在类名（与可选的类型参数）后。
 */

//带有主构造函数的 ,通常用constructor修饰 , 若不带有注解的constructor可省略
class Sample1Class (info : String) {
    lateinit var info : String
    //因为构造函数没有方法体,所以提供init方法
    //需要初始化数据时
    init {
        //doSomething();
    }
}

/**
 * 带有主构造函数的 ,通常用constructor修饰 , 若带有注解的constructor 不可省略
 */
//class @Inject internal Sample2Class (info : String) {

// }

/**
 * 带有次构造函数且未声明主构造函数的
 * 所有次构造函数在 init 执行后执行
 */
class Sample3Class{
    lateinit var info : String
    //次构造函数, constructor修饰
    constructor(info : String){
        //在init执行后执行
    }
    constructor(count:Int , info : String){
        //在init执行后执行
    }

    //需要初始化数据时
    init {
        //最早执行
        //doSomething();
    }
}

/**
 * 带有次构造函数且声明了主构造函数的, 次构造函数需要委托给主构造函数 ,使用this关键字
 * 并且主构造函数的参数, 次构造都要传给他
 * 所有次构造函数在 init 执行后执行
 */
class Sample4Class(val info: String) {
    constructor(info: String, content: String) : this(info) {
        //在init执行后执行
    }
    //需要初始化数据时
    init {
        //最早执行
        //doSomething();
    }
}