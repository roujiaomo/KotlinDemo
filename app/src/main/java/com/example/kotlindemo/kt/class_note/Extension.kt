package com.example.kotlindemo.kt.class_note

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * 扩展函数 (Kt特色)
 *
 * 可以拓展对象属性 可取代传统工具类传对象处理的方式
 * 可声明在file里 不用设置类名
 *
 * 拓展函数是静态的 不添加成员变量 只需要用类名直接调用
 */
class Extension {
    //  例 : 设置ImageView加载图片

    /**
     * 拓展函数写法
     * 外部可直接拿ImageView实例调用 imageView.loadUrl(context ,url),而不需要考虑当前类名
     * 外部调起对象传入方法体 可直接当做 this使用
     */
    fun ImageView.loadUrl(context: Context, url: String) {
        Glide.with(context).load(url).into(this)
    }

    //普通写法(这种写法和上面拓展函数写法在JVM里声明相同,两个方法不能共存)
//    fun loadUrl(imageView: ImageView, context: Context, url: String) {
//
//    }

}